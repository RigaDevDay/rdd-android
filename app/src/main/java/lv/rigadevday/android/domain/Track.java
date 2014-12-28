package lv.rigadevday.android.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "Tracks")
public class Track extends Model implements Serializable {

    @Column(name = "trackName")
    private String trackName;


    public Track() {
    }

    public Track(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public static Track getByName(String name) {
        return new Select().from(Track.class).where("trackName = ?", name).executeSingle();
    }

    public static List<Track> getAll() {
        return new Select().from(Track.class).execute();
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackName='" + trackName + '\'' +
                '}';
    }
}
