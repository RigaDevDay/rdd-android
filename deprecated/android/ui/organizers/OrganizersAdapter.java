package lv.rigadevday.android.ui.organizers;

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
import lv.rigadevday.android.repository.model.SponsorLogo;
import lv.rigadevday.android.ui.navigation.OpenWeb;
import lv.rigadevday.android.utils.BaseApplication;
import lv.rigadevday.android.utils.Utils;

/**
 */
public class OrganizersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TITLE = 1;
    public static final int TYPE_LOGO = 2;

    @Inject
    Context context;
    @Inject
    EventBus bus;
    @Inject
    Picasso picasso;

    List<SponsorLogo> logosList;

    public OrganizersAdapter(List<SponsorLogo> logosList) {
        BaseApplication.Companion.inject(this);
        this.logosList = logosList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == TYPE_LOGO
                ? new LogoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_organizers_logo, parent, false))
                : new TitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_organizers_title, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SponsorLogo item = logosList.get(position);

        if (getItemViewType(position) == TYPE_LOGO) {
            LogoViewHolder castedHolder = (LogoViewHolder) holder;

            picasso.load(Utils.imagePrefix(item.image))
                    .resize(120, 120)
                    .centerInside()
                    .into(castedHolder.picture);
            castedHolder.root.setOnClickListener(view -> bus.post(new OpenWeb(item.url)));
        } else {
            TitleViewHolder castedHolder = (TitleViewHolder) holder;

            castedHolder.title.setText(item.title);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return logosList.get(position).isHeader ? TYPE_TITLE : TYPE_LOGO;
    }

    @Override
    public int getItemCount() {
        return logosList.size();
    }


    public static class LogoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.organizers_item_root)
        public View root;
        @Bind(R.id.organizers_logo_item_image)
        public ImageView picture;

        public LogoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.organizers_logo_item_title)
        public TextView title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
