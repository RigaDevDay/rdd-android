package lv.rigadevday.android.repository.storage;

import java.util.Arrays;

/**
 */
public class FavoredTimeSlot {

    public String time;
    public String day;
    public int index;

    public FavoredTimeSlot() {
    }

    public FavoredTimeSlot(String day, String time, int index) {
        this.time = time;
        this.day = day;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoredTimeSlot that = (FavoredTimeSlot) o;

        return index == that.index
                && (time != null ? time.equals(that.time) : that.time == null
                && (day != null ? day.equals(that.day) : that.day == null));
    }

    @Override
    public int hashCode() {
        return Arrays.asList(day, time, index).hashCode();
    }
}
