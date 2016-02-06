package lv.rigadevday.android.repository;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.repository.model.Speaker;
import lv.rigadevday.android.repository.model.SponsorLogo;
import lv.rigadevday.android.repository.model.TimeSlot;
import lv.rigadevday.android.repository.model.venues.Venue;
import lv.rigadevday.android.repository.networking.DataFetchStub;
import lv.rigadevday.android.utils.BaseApplication;
import rx.Observable;

/**
 */
public class InMemoryStorage implements Repository {

    @Inject
    Context appContext;

    private static InMemoryStorage sInstance;

    public static InMemoryStorage getInstance() {
        if (sInstance == null) {
            sInstance = new InMemoryStorage();
        }
        return new InMemoryStorage();
    }

    private InMemoryStorage() {
        BaseApplication.inject(this);
    }

    @Override
    public Observable<Day> getAllDays() {
        return DataFetchStub.getData(appContext)
                .flatMap(dataRoot -> Observable.from(dataRoot.days))
                .cache();
    }

    @Override
    public Observable<Day> getDay(String title) {
        return getAllDays().filter(day -> day.title.equalsIgnoreCase(title)).first();
    }

    @Override
    public Observable<Speaker> getAllSpeakers() {
        return DataFetchStub.getData(appContext)
                .flatMap(dataRoot -> Observable.from(dataRoot.speakers))
                .cache();
    }

    @Override
    public Observable<Speaker> getSpeaker(int id) {
        return getAllSpeakers().filter(speaker -> speaker.id == id).first();
    }

    @Override
    public Observable<Speaker> getSpeakers(List<Integer> speakersIds) {
        return getAllSpeakers().filter(speaker -> speaker.isInList(speakersIds));
    }

    @Override
    public Observable<Integer> getVersion() {
        return DataFetchStub.getData(appContext)
                .flatMap(dataRoot -> Observable.just(dataRoot.version))
                .cache();
    }

    @Override
    public Observable<List<SponsorLogo>> getSponsors() {
        return DataFetchStub.getSponsors(appContext);
    }

    @Override
    public Observable<TimeSlot> getTimeSlot(String filterDay, String filterTime) {
        return DataFetchStub.getData(appContext)
                .flatMap(data -> Observable.from(data.days))
                .cache()
                .filter(day -> day.title.equalsIgnoreCase(filterDay))
                .flatMap(day -> Observable.from(day.schedule.schedule))
                .filter(timeSlot -> timeSlot.time.equalsIgnoreCase(filterTime))
                .first();
    }

    @Override
    public Observable<Venue> getVenue(String title) {
        return DataFetchStub.getVenues(appContext)
                .flatMap(Observable::from)
                .cache()
                .filter(venue -> venue.title.equalsIgnoreCase(title))
                .first();
    }
}
