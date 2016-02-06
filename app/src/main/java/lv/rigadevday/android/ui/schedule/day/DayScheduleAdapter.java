package lv.rigadevday.android.ui.schedule.day;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.Repository;
import lv.rigadevday.android.repository.model.Event;
import lv.rigadevday.android.repository.model.TimeSlot;
import lv.rigadevday.android.ui.navigation.OpenTalkEvent;
import lv.rigadevday.android.utils.BaseApplication;
import lv.rigadevday.android.utils.Utils;

/**
 */
public class DayScheduleAdapter extends RecyclerView.Adapter {

    private final int TYPE_CARDS_LIST = 1;
    private final int TYPE_SINGLE_CARD = 2;

    @Inject
    Repository repository;

    @Inject
    EventBus bus;

    private final String day;

    private List<TimeSlot> mSchedule;

    public DayScheduleAdapter(String day, List<TimeSlot> schedule) {
        this.day = day;
        this.mSchedule = schedule;
        BaseApplication.inject(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (mSchedule.get(position).events.size() > 1)
            return TYPE_CARDS_LIST;
        else
            return TYPE_SINGLE_CARD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == TYPE_CARDS_LIST
                ? R.layout.item_timeslot_cards_list
                : R.layout.item_timeslot_single_card;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return viewType == TYPE_CARDS_LIST
                ? new CardsListViewHolder(view)
                : new SingleCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        TimeSlot item = mSchedule.get(position);

        if (getItemViewType(position) == TYPE_CARDS_LIST) {
            setCardsList((CardsListViewHolder) genericHolder, item);
        } else {
            setBigCard((SingleCardViewHolder) genericHolder, item);
        }
    }

    private void setCardsList(CardsListViewHolder holder, TimeSlot item) {
        holder.timeLabel.setText(item.time);

        for (int index = 0; index < item.events.size(); index++) {
            fillCard(holder.cards.get(index),
                    holder.subtitleLabels.get(index),
                    holder.speakerLabels.get(index),
                    item.events.get(index),
                    item.time,
                    index);
        }
    }

    private void fillCard(CardView card, TextView subtitle, TextView title, Event event, final String time, final int index) {
        card.setVisibility(View.VISIBLE);
        subtitle.setText(event.subtitle);

        if (event.speakers != null) {
            setSpeakerNames(title, event.speakers);
            card.setClickable(true);

            card.setOnClickListener(v -> bus.post(new OpenTalkEvent(day, time, index)));
        } else {
            subtitle.setText("");
            card.setClickable(false);
        }
    }

    private void setBigCard(SingleCardViewHolder holder, TimeSlot item) {
        holder.timeLabel.setText(item.time);

        Event event = item.events.get(0);
        if (Utils.isNullOrEmpty(event.title)) {
            holder.titleLabel.setVisibility(View.VISIBLE);
            holder.titleLabel.setText(event.subtitle);

            setSpeakerNames(holder.speakerLabel, event.speakers);

            holder.card.setClickable(true);
            holder.card.setOnClickListener(v -> bus.post(new OpenTalkEvent(day, item.time, 0)));
        } else {
            holder.titleLabel.setVisibility(View.GONE);

            holder.speakerLabel.setText(event.title);
            holder.card.setClickable(false);
        }
    }

    private void setSpeakerNames(TextView speakerLabel, List<Integer> speakers) {
        repository.getSpeakers(speakers)
                .map(speaker -> speaker.name)
                .defaultIfEmpty("")
                .reduce((r, s) -> r.concat(", ").concat(s))
                .subscribe(speakerLabel::setText);
    }

    @Override
    public int getItemCount() {
        return mSchedule.size();
    }

    public static class CardsListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.timeslot_time_label)
        public TextView timeLabel;

        // This is a bit wrong, but it works perfectly
        @Bind({
                R.id.timeslot_room1,
                R.id.timeslot_room2,
                R.id.timeslot_room3,
                R.id.timeslot_room4,
                R.id.timeslot_room5
        })
        public List<CardView> cards;

        @Bind({
                R.id.timeslot_speaker_1,
                R.id.timeslot_speaker_2,
                R.id.timeslot_speaker_3,
                R.id.timeslot_speaker_4,
                R.id.timeslot_speaker_5
        })
        public List<TextView> speakerLabels;

        @Bind({
                R.id.timeslot_title_1,
                R.id.timeslot_title_2,
                R.id.timeslot_title_3,
                R.id.timeslot_title_4,
                R.id.timeslot_title_5
        })
        public List<TextView> subtitleLabels;

        public CardsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SingleCardViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.timeslot_time_label)
        public TextView timeLabel;

        @Bind(R.id.timeslot_all_rooms)
        CardView card;

        @Bind(R.id.timeslot_all_rooms_speaker)
        public TextView speakerLabel;

        @Bind(R.id.timeslot_all_rooms_title)
        public TextView titleLabel;

        public SingleCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
