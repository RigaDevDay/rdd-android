package lv.rigadevday.android.integration.json;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lv.rigadevday.android.domain.ScheduleSlot;

@Table(name = "ScheduleSlot")
public class ScheduleSlotDto extends Model {

    @SerializedName("time")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("icon")
    private String icon;
    @SerializedName("events")
    private List<PresentationDto> presentations;

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

    public List<PresentationDto> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<PresentationDto> presentations) {
        this.presentations = presentations;
    }

    public ScheduleSlot toScheduleSlot() {
        ScheduleSlot slot = new ScheduleSlot();
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setIcon(icon);
        return slot;
    }
}
