package lv.rigadevday.android.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.Observable;
import rx.Subscriber;

/**
 */
public class AssetFileParser {

    private AssetFileParser() {
    }

    public static <T> Observable<T> parseFile(Context ctx, String fileName, Class<T> clazz) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                InputStreamReader reader = null;
                try {
                    InputStream stream = ctx.getAssets().open(fileName);
                    reader = new InputStreamReader(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

                Gson gson = new GsonBuilder().create();

                if (reader != null) {
                    subscriber.onNext(gson.fromJson(reader, clazz));

                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        });
    }
}
