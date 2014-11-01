package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "ScheduleSlot")
public class ScheduleSlot extends Model {

    @Column(name = "time")
    private String startTime;
    @Column(name = "endTime")
    private String endTime;
    @Column(name = "icon")
    private String icon;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String time) {
        this.startTime = time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Presentation> getPresentations() {
        return getMany(Presentation.class, "Id");
    }

}
