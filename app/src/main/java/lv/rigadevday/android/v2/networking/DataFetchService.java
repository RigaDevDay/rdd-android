package lv.rigadevday.android.v2.networking;


import lv.rigadevday.android.v2.model.DataRoot;
import retrofit.http.GET;
import rx.Observable;

/**
 */
public interface DataFetchService {

    @GET("/assets/data/main.json")
    Observable<DataRoot> getData();
}
