package lv.rigadevday.android.repository.networking;


import lv.rigadevday.android.repository.model.DataRoot;
import retrofit.http.GET;
import rx.Observable;

/**
 */
public interface DataFetchService {

    @GET(DataUrls.MAIN_FILE_ADDRESS)
    Observable<DataRoot> getData();
}
