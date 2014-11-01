package lv.rigadevday.android.integration.data;

import java.util.List;

public class ScheduleDto {

    private List<String> roomNames;
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
}
