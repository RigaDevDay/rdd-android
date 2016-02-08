package lv.rigadevday.android.repository.networking;

import android.content.Context;

import lv.rigadevday.android.repository.AssetFileParser;
import lv.rigadevday.android.repository.model.DataRoot;
import rx.Observable;

/**
 */
public class DataFetchStub {

    private DataRoot cachedData;

    public DataFetchStub() {
    }

    public Observable<DataRoot> getData(Context ctx) {
        if (cachedData == null) {
            return AssetFileParser.parseFile(ctx, "main.json", DataRoot.class)
                    .cache()
                    .map(dataRoot -> {
                        cachedData = dataRoot;
                        return cachedData;
                    });
        }
        return Observable.just(cachedData);
    }

}
