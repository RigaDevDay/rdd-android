package lv.rigadevday.android.repository;

import android.content.res.Resources;

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

    Observable<Speaker> getSpeakers(String id);

    Observable<Integer> getVersion();

    Observable<List<SponsorLogo>> getSponsors();
}
