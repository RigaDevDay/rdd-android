package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "Presentations")
public class Presentation extends Model implements Serializable {

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "bookmarked")
    private boolean bookmarked;
    @Column(name = "startTime")
    private Date startTime;
    @Column(name = "endTime")
    private Date endTime;
    @Column(name = "room")
    private String room;

    private List<Speaker> speakers;
    private List<Tag> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<Speaker> getSpeakers() {
        if (speakers == null) {
            speakers = new Select()
                    .from(Speaker.class)
                    .innerJoin(SpeakerPresentation.class).on("Speakers.id = SpeakerPresentation.speaker")
                    .where("SpeakerPresentation.presentation = ?", getId())
                    .execute();
        }
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public static List<Presentation> getAll() {
        return new Select().from(Presentation.class).execute();
    }

    public static List<Presentation> getBookmarked() {
        return new Select().from(Presentation.class).where("bookmarked = ?", true).execute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Presentation that = (Presentation) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", bookmarked=" + bookmarked +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
