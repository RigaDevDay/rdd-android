package lv.rigadevday.android.ui.navigation;

/**
 */
public class OpenTalkEvent {

    public final String day;
    public final String time;
    public final int index;

    public OpenTalkEvent(String day, String time, int index) {
        this.day = day;
        this.time = time;
        this.index = index;
    }
}
