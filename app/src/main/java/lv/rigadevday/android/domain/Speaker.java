package lv.rigadevday.android.domain;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.ViewHolder;
import lv.rigadevday.android.domain.reference.SpeakerType;
import lv.rigadevday.android.ui.custom.view.parallax.ParallaxListItem;

@Table(name = "Speakers")
public class Speaker extends Model implements Serializable, ParallaxListItem {
    public static final String BACKSTAGE_DRAWABLE = "backstage_";
    public static final String PHOTO_DRAWABLE = "speaker_";

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "lineup")
    private Integer lineup;

    @Column(name = "name")
    private String name;

    @Column(name = "bio")
    private String bio;

    @Column(name = "country")
    private String country;

    @Column(name = "company")
    private String company;

    @Column(name = "type")
    private int type;

    private List<Contact> contacts;
    private List<Presentation> presentations;
    private int backstageImageResource;
    private int profileImageResource;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getLineup() {
        return lineup;
    }

    public void setLineup(Integer lineup) {
        this.lineup = lineup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public SpeakerType getType() {
        return SpeakerType.getById(type);
    }

    public void setType(SpeakerType type) {
        this.type = type.getId();
    }

    public List<Contact> getContacts() {
        if (contacts == null) {
            contacts = getMany(Contact.class, "speaker");
        }
        return contacts;
    }

    public List<Presentation> getPresentations() {
        if (presentations == null) {
            presentations = new Select()
                    .from(Presentation.class)
                    .innerJoin(SpeakerPresentation.class).on("Presentations.id = SpeakerPresentation.presentation")
                    .where("SpeakerPresentation.speaker = ?", getId())
                    .execute();
        }
        return presentations;
    }

    public boolean isBookmarked() {
        for (Presentation presentation : getPresentations()) {
            if (presentation.isBookmarked()) return true;
        }
        return false;
    }

    public static List<Speaker> getAll() {
        return new Select().from(Speaker.class).execute();
    }

    public static Speaker getByUid(Integer uid) {
        return new Select().from(Speaker.class).where("uid = ?", uid).executeSingle();
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                ", lineup=" + lineup +
                '}';
    }

    public int getBackstageImageResource(Context ctx) {
        if (backstageImageResource == 0) {
            backstageImageResource = ctx.getResources().getIdentifier(BACKSTAGE_DRAWABLE + uid, "drawable", ctx.getPackageName());
        }
        return backstageImageResource;
    }

    public int getImageResource(Context ctx) {
        if (profileImageResource == 0) {
            int id = ctx.getResources().getIdentifier(PHOTO_DRAWABLE + uid, "drawable", ctx.getPackageName());
            profileImageResource = (id == 0) ? R.drawable.speaker_0 : id;
        }
        return profileImageResource;
    }

    @Override
    public int getItemLayoutResource() {
        return R.layout.speaker_list_item;
    }

    @Override
    public void present(View view, Context ctx) {

        TextView speakerName = ViewHolder.get(view, R.id.speaker_list_item_name);
        speakerName.setText(name);

        TextView speakerCompany = ViewHolder.get(view, R.id.speaker_list_item_company);
        speakerCompany.setText(company);

        ImageView speakerImage = ViewHolder.get(view, R.id.speaker_list_item_image);

        ImageView backstage = ViewHolder.get(view, R.id.item_background);

        Picasso.with(ctx)
                .load(getBackstageImageResource(ctx))
                .priority(Picasso.Priority.HIGH)
                .into(backstage);


        Picasso.with(ctx)
                .load(getImageResource(ctx))
                .placeholder(R.drawable.speaker_0)
                .priority(Picasso.Priority.HIGH)
                .into(speakerImage);
    }
}
