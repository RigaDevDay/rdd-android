package lv.rigadevday.android.utils.connectivity;

import android.content.Context;

import javax.inject.Inject;

/**
 */
public class DownloadManager {

    @Inject
    Context appContext;

    private static DownloadManager sInstance;

    public static DownloadManager getInstance() {
        if (sInstance == null)
            sInstance = new DownloadManager();
        return new DownloadManager();
    }

    private static final String REQUEST_URL = "https://raw.githubusercontent.com/RigaDevDay/RigaDevDay.github.io/master/assets/data/main.json";
/*
    private static RequestQueue mDownloadQueue;

    public DownloadManager() {
        BaseApplication.inject(this);
        mDownloadQueue = Volley.newRequestQueue(appContext);
    }

    public StringRequest getConfiguration() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         Gson gson = new Gson();
                        gson.fromJson(response, DataRoot.class);
                        // TODO: do something with the response
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: do something with error the response
            }
        }

        );
        mDownloadQueue.add(stringRequest);
        return stringRequest;
    }*/
}
