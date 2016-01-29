package lv.rigadevday.android.v2.repository;

import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.v2.model.Day;
import lv.rigadevday.android.v2.model.Speaker;
import lv.rigadevday.android.v2.networking.DataFetchStub;
import rx.Observable;

/**
 */
public class InMemoryStorage implements Repository {

    private static InMemoryStorage sInstance;

    public static InMemoryStorage getInstance() {
        if (sInstance == null)
            sInstance = new InMemoryStorage();
        return new InMemoryStorage();
    }

    private InMemoryStorage() {
    }

    @Override
    public Observable<Day> getAllDays() {
        return DataFetchStub.getData(BaseApplication.getContext())
                .flatMap(dataRoot -> Observable.from(dataRoot.days))
                .cache();
    }

    @Override
    public Observable<Day> getDay(String title) {
        return getAllDays().filter(day -> day.title.equalsIgnoreCase(title)).first();
    }

    @Override
    public Observable<Speaker> getAllSpeakers() {
        return DataFetchStub.getData(BaseApplication.getContext())
                .flatMap(dataRoot -> Observable.from(dataRoot.speakers))
                .cache();
    }

    @Override
    public Observable<Speaker> getSpeakers(String id) {
        return getAllSpeakers().filter(speaker -> speaker.id.equalsIgnoreCase(id)).first();
    }
}
