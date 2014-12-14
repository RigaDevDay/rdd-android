package lv.rigadevday.android.domain.dto;

import com.google.gson.annotations.SerializedName;

public class SpeakerInfo {

    @SerializedName("id")
    private Integer id;

    @SerializedName("order")
    private Integer lineup;

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String bio;

    @SerializedName("country")
    private String country;

    @SerializedName("type")
    private String type;

    @SerializedName("company")
    private String company;

    @SerializedName("contacts")
    private ContactInfo contact;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpeakerInfo that = (SpeakerInfo) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "SpeakerInfo{" +
                "id=" + id +
                ", lineup=" + lineup +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", company='" + company + '\'' +
                ", contact=" + contact +
                '}';
    }
}
