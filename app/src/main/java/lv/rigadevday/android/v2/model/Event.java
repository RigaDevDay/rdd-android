package lv.rigadevday.android.v2.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

/**
 */
public class Event {

    public String title;
    public String subtitle;
    public String description;
    public List<String> speakers;
    public List<String> tags;

    public String speaker() {
        if (speakers != null)
            return Stream.of(speakers).collect(Collectors.joining(", "));
        return "";
    }
}
