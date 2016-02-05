package lv.rigadevday.android.repository;

import android.content.Context;
import android.content.res.Resources;

import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.repository.model.SponsorLogo;
import lv.rigadevday.android.utils.BaseApplication;
import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.repository.model.Speaker;
import lv.rigadevday.android.repository.networking.DataFetchStub;
import rx.Observable;

/**
 */
public class InMemoryStorage implements Repository {

    @Inject
    Context appContext;

    private static InMemoryStorage sInstance;

    public static InMemoryStorage getInstance() {
        if (sInstance == null)
            sInstance = new InMemoryStorage();
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
    public Observable<Speaker> getSpeakers(String id) {
        return getAllSpeakers().filter(speaker -> speaker.id.equalsIgnoreCase(id)).first();
    }

    @Override
    public Observable<List<SponsorLogo>> getSponsors(Resources res) {
        return DataFetchStub.getSponsors(appContext);
    }
}
