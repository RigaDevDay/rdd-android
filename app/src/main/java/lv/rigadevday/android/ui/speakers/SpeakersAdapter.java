package lv.rigadevday.android.ui.speakers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.model.Speaker;
import lv.rigadevday.android.ui.navigation.OpenSpeakerScreen;
import lv.rigadevday.android.utils.BaseApplication;
import lv.rigadevday.android.utils.Utils;

/**
 */
public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.SpeakerViewHolder> {

    @Inject
    Context context;

    @Inject
    EventBus bus;

    @Inject
    Picasso picasso;

    List<Speaker> speakersList;

    public SpeakersAdapter(List<Speaker> list) {
        BaseApplication.inject(this);
        this.speakersList = list;
    }

    @Override
    public SpeakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpeakerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_speakers, parent, false));
    }

    @Override
    public void onBindViewHolder(SpeakerViewHolder holder, int position) {
        Speaker speaker = speakersList.get(position);

        picasso.load(Utils.imagePrefix(speaker.img))
                .fit()
                .placeholder(R.drawable.vector_speaker_placeholder)
                .into(holder.picture);

        holder.name.setText(speaker.name);
        holder.company.setText(speaker.company);

        holder.root.setOnClickListener(view -> bus.post(new OpenSpeakerScreen(speaker.id)));
    }

    @Override
    public int getItemCount() {
        return speakersList.size();
    }

    public static class SpeakerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.speakers_item_root)
        public View root;

        @Bind(R.id.speakers_item_image)
        public ImageView picture;

        @Bind(R.id.speakers_item_name)
        public TextView name;

        @Bind(R.id.speakers_item_company)
        public TextView company;

        public SpeakerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
