package lv.rigadevday.android.repository.model;

import java.util.List;

/**
 */
public class Speaker {

    public int id;
    public String name;
    public String company;
    public String title;
    public String country;
    public String img;
    public String twitter;
    public String blog;
    public String linkedin;
    public String description;

    public Boolean isInList(List<Integer> speakersIds) {
        return speakersIds != null && speakersIds.contains(id);
    }
}
