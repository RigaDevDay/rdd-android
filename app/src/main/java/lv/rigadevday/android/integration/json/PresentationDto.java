package lv.rigadevday.android.integration.json;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lv.rigadevday.android.domain.Presentation;

public class PresentationDto extends Model {

    @SerializedName("title")
    private String title;
    @SerializedName("subtitle")
    private String subtitle;
    @SerializedName("description")
    private String description;
    @SerializedName("speakers")
    private List<String> speakers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<String> speakers) {
        this.speakers = speakers;
    }

    public static List<PresentationDto> getAll() {
        return new Select().from(PresentationDto.class).execute();
    }

    public Presentation toPresentation(int size) {
        Presentation presentation = new Presentation();
        presentation.setTitle(title);
        presentation.setSubtitle(subtitle);
        presentation.setDescription(description);
        presentation.setHeader(size == 1);
        //TODO: Add speakers
        return presentation;
    }
}
