package lv.rigadevday.android.repository;

import java.util.List;

import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.repository.model.Speaker;
import lv.rigadevday.android.repository.model.SponsorLogo;
import rx.Observable;

/**
 */
public interface Repository {

    Observable<Day> getAllDays();

    Observable<Day> getDay(String title);

    Observable<Speaker> getAllSpeakers();

    Observable<Speaker> getSpeaker(int id);

    Observable<Speaker> getSpeakers(List<Integer> speakers);

    Observable<Integer> getVersion();

    Observable<List<SponsorLogo>> getSponsors();

}
