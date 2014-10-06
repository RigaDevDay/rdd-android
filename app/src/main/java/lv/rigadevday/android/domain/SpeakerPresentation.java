package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "SpeakerPresentation")
public class SpeakerPresentation extends Model {

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
}
