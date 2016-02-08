package lv.rigadevday.android.repository.networking;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import lv.rigadevday.android.repository.AssetFileParser;
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
        return AssetFileParser.parseFile(ctx, "main.json", DataRoot.class).cache();
    }

}
