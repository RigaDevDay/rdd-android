package lv.rigadevday.android.repository.networking;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;

import lv.rigadevday.android.BuildConfig;

/**
 */
public class HttpClientConfig {


    public static final int CACHE_TIMEOUT = 60 * 60 * 3; // seconds * minutes * hours
    public static final int CACHE_SIZE = 2 * 1024 * 1024; // 2 MiB


    private HttpClientConfig() {
    }

    public static void setCache(Context context, OkHttpClient httpClient) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
        httpClient.setCache(cache);
    }

    public static void setCacheInterceptor(OkHttpClient httpClient) {
        httpClient.networkInterceptors().add(
                chain -> {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", String.format("public, max-age=%d", CACHE_TIMEOUT))
                            .build();
                }
        );
    }

}
