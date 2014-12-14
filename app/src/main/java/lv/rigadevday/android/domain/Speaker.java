package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

import lv.rigadevday.android.domain.reference.SpeakerType;

@Table(name = "Speakers")
public class Speaker extends Model implements Serializable {

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
        return SpeakerType.byId(type);
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
}
