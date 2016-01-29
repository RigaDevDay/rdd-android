package lv.rigadevday.android.v2.repository;

import lv.rigadevday.android.v2.model.Day;
import lv.rigadevday.android.v2.model.Speaker;
import rx.Observable;

/**
 */
public interface Repository {

    Observable<Day> getAllDays();

    Observable<Day> getDay(String title);

    Observable<Speaker> getAllSpeakers();

    Observable<Speaker> getSpeakers(String id);
}
