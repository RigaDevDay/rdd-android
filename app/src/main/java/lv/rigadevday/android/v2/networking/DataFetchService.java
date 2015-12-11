package lv.rigadevday.android.v2.networking;

import lv.rigadevday.android.v2.model.DataRoot;
import retrofit.http.GET;

/**
 */
public interface DataFetchService {

    @GET("/assets/data/main.json")
    DataRoot getData();
}
