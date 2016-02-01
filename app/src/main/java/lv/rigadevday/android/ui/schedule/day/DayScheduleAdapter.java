package lv.rigadevday.android.ui.schedule.day;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lv.rigadevday.android.R;
import lv.rigadevday.android.utils.Utils;
import lv.rigadevday.android.repository.model.Event;
import lv.rigadevday.android.repository.model.TimeSlot;

/**
 */
public class DayScheduleAdapter extends RecyclerView.Adapter {

    private int TYPE_CARDS_LIST = 1;
    private int TYPE_SINGLE_CARD = 2;

    private List<TimeSlot> mSchedule;

    public DayScheduleAdapter(List<TimeSlot> schedule) {
        this.mSchedule = schedule;
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

        for (int i = 0; i < item.events.size(); i++) {
            Event event = item.events.get(i);

            holder.cards.get(i).setVisibility(View.VISIBLE);
            holder.speakerLabels.get(i).setText(event.speaker());
            holder.subtitleLabels.get(i).setText(event.subtitle);
        }
    }

    private void setBigCard(SingleCardViewHolder holder, TimeSlot item) {
        holder.timeLabel.setText(item.time);

        Event event = item.events.get(0);
        if (Utils.isNullOrEmpty(event.title)) {
            holder.titleLabel.setVisibility(View.VISIBLE);
            holder.titleLabel.setText(event.subtitle);

            holder.speakerLabel.setText(event.speaker());
        } else {
            holder.titleLabel.setVisibility(View.GONE);

            holder.speakerLabel.setText(event.title);
        }
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
