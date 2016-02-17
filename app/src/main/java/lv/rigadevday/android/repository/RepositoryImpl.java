package lv.rigadevday.android.repository;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.repository.model.Speaker;
import lv.rigadevday.android.repository.model.SponsorLogoList;
import lv.rigadevday.android.repository.model.TimeSlot;
import lv.rigadevday.android.repository.model.venues.Venue;
import lv.rigadevday.android.repository.model.venues.VenuesList;
import lv.rigadevday.android.repository.networking.DataFetchService;
import lv.rigadevday.android.repository.storage.Storage;
import lv.rigadevday.android.utils.BaseApplication;
import rx.Observable;

/**
 */
public class RepositoryImpl implements Repository {

    private static RepositoryImpl INSTANCE;
    @Inject
    Context appContext;
    @Inject
    DataFetchService dataFetchService;
    private Storage storage;

    private RepositoryImpl() {
        BaseApplication.inject(this);
        storage = new Storage();
    }

    public static RepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryImpl();
        }
        return new RepositoryImpl();
    }

    @Override
    public Observable<Day> getAllDays() {
        return dataFetchService.getData()
                .flatMap(dataRoot -> Observable.from(dataRoot.days))
                .cache();
    }

    @Override
    public Observable<Day> getDay(String title) {
        return getAllDays().filter(day -> day.title.equalsIgnoreCase(title)).first();
    }


    @Override
    public Observable<Speaker> getAllSpeakers() {
        return dataFetchService.getData()
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
    public Observable<TimeSlot> getTimeSlot(String filterDay, String filterTime) {
        return dataFetchService.getData()
                .flatMap(data -> Observable.from(data.days))
                .cache()
                .filter(day -> day.title.equalsIgnoreCase(filterDay))
                .flatMap(day -> Observable.from(day.schedule.schedule))
                .filter(timeSlot -> timeSlot.time.equalsIgnoreCase(filterTime))
                .first();
    }


    @Override
    public Observable<SponsorLogoList> getSponsors() {
        return AssetFileParser.parseFile(appContext, "sponsors.json", SponsorLogoList.class).cache();
    }

    @Override
    public Observable<Venue> getVenue(String title) {
        return AssetFileParser.parseFile(appContext, "venues.json", VenuesList.class).cache()
                .flatMap(Observable::from)
                .cache()
                .filter(venue -> venue.title.equalsIgnoreCase(title))
                .first();
    }


    @Override
    public Observable<Boolean> hasFavoredTimeSlot(String day, String time, int index) {
        return storage.hasFavoredTimeSlot(day, time, index);
    }

    @Override
    public Observable<Boolean> toggleTimeSlotFavored(String day, String time, int index) {
        return storage.saveOrDeleteFavoredTimeSlot(day, time, index);
    }
}
