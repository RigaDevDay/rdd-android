package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "PresentationTag")
public class PresentationTag extends Model implements Serializable {

    @Column(name = "presentation", onDelete = Column.ForeignKeyAction.RESTRICT)
    private Presentation presentation;
    @Column(name = "tag", onDelete = Column.ForeignKeyAction.RESTRICT)
    private Tag tag;

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public static List<PresentationTag> getByTag(Long tagId) {
        return new Select().from(PresentationTag.class).where("tag = ?", tagId).execute();
    }

    public static List<PresentationTag> getByPresentation(Long presentaitonId) {
        return new Select().from(PresentationTag.class).where("presentation = ?", presentaitonId).execute();
    }
}
