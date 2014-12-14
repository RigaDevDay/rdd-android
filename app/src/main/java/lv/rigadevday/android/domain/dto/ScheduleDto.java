package lv.rigadevday.android.domain.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleDto {

    @SerializedName("roomNames")
    private List<String> roomNames;
    @SerializedName("schedule")
    private List<ScheduleSlotDto> schedule;

    public List<String> getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(List<String> roomNames) {
        this.roomNames = roomNames;
    }

    public List<ScheduleSlotDto> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleSlotDto> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "roomNames=" + roomNames +
                ", schedule=" + schedule +
                '}';
    }
}
