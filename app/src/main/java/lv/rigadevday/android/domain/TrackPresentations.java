package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "TrackPresentations")
public class TrackPresentations extends Model implements Serializable {

    @Column(name = "presentation", onDelete = Column.ForeignKeyAction.RESTRICT)
    private Presentation presentation;
    @Column(name = "track", onDelete = Column.ForeignKeyAction.RESTRICT)
    private Track track;

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public static List<TrackPresentations> getAll() {
        return new Select().from(TrackPresentations.class).execute();
    }

    public static List<TrackPresentations> getByTrack(Long trackId) {
        return new Select().from(TrackPresentations.class).where("track = ?", trackId).execute();
    }

    public static List<TrackPresentations> getByPresentation(Long presentaitonId) {
        return new Select().from(TrackPresentations.class).where("presentation = ?", presentaitonId).execute();
    }

}
