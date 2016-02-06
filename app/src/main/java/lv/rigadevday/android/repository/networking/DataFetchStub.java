package lv.rigadevday.android.repository.networking;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import lv.rigadevday.android.repository.model.DataRoot;
import lv.rigadevday.android.repository.model.SponsorLogo;
import lv.rigadevday.android.repository.model.SponsorLogoList;
import lv.rigadevday.android.repository.model.venues.Venue;
import lv.rigadevday.android.repository.model.venues.VenuesList;
import rx.Observable;
import rx.Subscriber;

/**
 */
public class DataFetchStub {

    private DataFetchStub() {
    }

    public static Observable<DataRoot> getData(Context ctx) {
        return Observable.create(new Observable.OnSubscribe<DataRoot>() {
            @Override
            public void call(Subscriber<? super DataRoot> subscriber) {
                InputStreamReader reader = null;
                try {
                    InputStream stream = ctx.getAssets().open("main.json");
                    reader = new InputStreamReader(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

                Gson gson = new GsonBuilder().create();

                if (reader != null) {
                    subscriber.onNext(gson.fromJson(reader, DataRoot.class));

                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).cache();
    }

    public static Observable<List<SponsorLogo>> getSponsors(Context ctx) {
        return Observable.create(new Observable.OnSubscribe<List<SponsorLogo>>() {
            @Override
            public void call(Subscriber<? super List<SponsorLogo>> subscriber) {
                InputStreamReader reader = null;
                try {
                    InputStream stream = ctx.getAssets().open("sponsors.json");
                    reader = new InputStreamReader(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

                Gson gson = new GsonBuilder().create();

                if (reader != null) {
                    subscriber.onNext(gson.fromJson(reader, SponsorLogoList.class));

                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).cache();
    }

    public static Observable<List<Venue>> getVenues(Context ctx) {
        return Observable.create(new Observable.OnSubscribe<List<Venue>>() {
            @Override
            public void call(Subscriber<? super List<Venue>> subscriber) {
                InputStreamReader reader = null;
                try {
                    InputStream stream = ctx.getAssets().open("venues.json");
                    reader = new InputStreamReader(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

                Gson gson = new GsonBuilder().create();

                if (reader != null) {
                    subscriber.onNext(gson.fromJson(reader, VenuesList.class));

                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).cache();
    }

}
