package lv.rigadevday.android.repository.networking;


import lv.rigadevday.android.repository.model.DataRoot;
import retrofit.http.GET;
import rx.Observable;

/**
 */
public interface DataFetchService {

    @GET("/assets/data/main.json")
    Observable<DataRoot> getData();
}
