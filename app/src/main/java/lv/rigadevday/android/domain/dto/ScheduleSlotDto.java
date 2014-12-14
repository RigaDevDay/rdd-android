package lv.rigadevday.android.domain.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleSlotDto {

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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
}
