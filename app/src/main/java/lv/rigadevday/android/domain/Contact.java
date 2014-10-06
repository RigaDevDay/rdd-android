package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Contacts")
public class Contact extends Model {

    @Column(name = "type")
    private int type;
    @Column(name = "value")
    private String value;
    @Column(name = "speaker")
    private Speaker speaker;

    public ContactType getType() {
        return ContactType.byId(type);
    }

    public void setType(ContactType type) {
        this.type = type.getId();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
