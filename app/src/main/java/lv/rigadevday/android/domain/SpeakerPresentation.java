package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "SpeakerPresentation")
public class SpeakerPresentation extends Model implements Serializable {

    @Column(name = "speaker", onDelete = Column.ForeignKeyAction.RESTRICT)
    private Speaker speaker;
    @Column(name = "presentation", onDelete = Column.ForeignKeyAction.RESTRICT)
    private Presentation presentation;

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public static List<SpeakerPresentation> getAll() {
        return new Select().from(SpeakerPresentation.class).execute();
    }

    public static List<SpeakerPresentation> getBySpeaker(Long speakerId) {
        return new Select().from(SpeakerPresentation.class).where("speaker = ?", speakerId).execute();
    }

    public static List<SpeakerPresentation> getByPresentation(Long presentaitonId) {
        return new Select().from(SpeakerPresentation.class).where("presentation = ?", presentaitonId).execute();
    }
}
