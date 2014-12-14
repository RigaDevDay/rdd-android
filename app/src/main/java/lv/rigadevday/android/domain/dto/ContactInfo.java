package lv.rigadevday.android.domain.dto;

import com.google.gson.annotations.SerializedName;

public class ContactInfo {

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("blog")
    private String blog;

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "twitter='" + twitter + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
