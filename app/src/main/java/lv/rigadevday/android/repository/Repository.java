package lv.rigadevday.android.repository;

import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.repository.model.Speaker;
import rx.Observable;

/**
 */
public interface Repository {

    Observable<Day> getAllDays();

    Observable<Day> getDay(String title);

    Observable<Speaker> getAllSpeakers();

    Observable<Speaker> getSpeakers(String id);
}
