package lv.rigadevday.android.integration.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleSlotDto {

    @SerializedName("time")
    private String time;

    @SerializedName("events")
    private List<EventDto> events;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<EventDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventDto> events) {
        this.events = events;
    }
}
