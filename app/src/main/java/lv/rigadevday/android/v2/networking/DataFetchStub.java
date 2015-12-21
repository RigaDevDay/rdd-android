package lv.rigadevday.android.v2.networking;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lv.rigadevday.android.v2.model.DataRoot;
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
                }
                subscriber.onCompleted();
            }
        });

    }

}
