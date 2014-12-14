package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "Tags")
public class Tag extends Model implements Serializable {

    @Column(name = "name", unique = true, index = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String tag) {
        this.name = tag;
    }

    public static Tag getByName(String tag) {
        return new Select().from(Tag.class).where("name = ?", tag).executeSingle();
    }

    public static List<Tag> getAll() {
        return new Select().from(Tag.class).execute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tag tag1 = (Tag) o;

        if (name != null ? !name.equals(tag1.name) : tag1.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
