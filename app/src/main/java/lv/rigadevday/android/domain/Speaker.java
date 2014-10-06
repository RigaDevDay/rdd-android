package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Speakers")
public class Speaker extends Model {

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

    public List<Contact> getContacts() {
        return getMany(Contact.class, "speaker");
    }

    public static List<Speaker> getAll() {
        return new Select().from(Speaker.class).execute();
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
