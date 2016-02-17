package lv.rigadevday.android.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.repository.Repository;
import lv.rigadevday.android.repository.RepositoryImpl;
import lv.rigadevday.android.repository.networking.DataFetchService;
import lv.rigadevday.android.repository.networking.HttpClientConfig;
import lv.rigadevday.android.repository.storage.Storage;
import lv.rigadevday.android.ui.base.BaseActivity;
import lv.rigadevday.android.ui.drawer.DrawerActivity;
import lv.rigadevday.android.ui.licences.LicencesActivity;
import lv.rigadevday.android.ui.organizers.OrganizersAdapter;
import lv.rigadevday.android.ui.organizers.OrganizersFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.schedule.day.DayScheduleAdapter;
import lv.rigadevday.android.ui.schedule.day.DayScheduleFragment;
import lv.rigadevday.android.ui.speakers.SpeakersAdapter;
import lv.rigadevday.android.ui.speakers.SpeakersListFragment;
import lv.rigadevday.android.ui.speakers.speaker.SpeakerActivity;
import lv.rigadevday.android.ui.speakers.speaker.SpeakerFragment;
import lv.rigadevday.android.ui.talk.TalkActivity;
import lv.rigadevday.android.ui.talk.TalkFragment;
import lv.rigadevday.android.ui.venues.VenueFragment;
import lv.rigadevday.android.ui.venues.VenuesRootFragment;
import lv.rigadevday.android.utils.BaseApplication;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module(
        injects = {
                BaseApplication.class,

                BaseActivity.class,
                DrawerActivity.class,
                TalkActivity.class,
                SpeakerActivity.class,
                LicencesActivity.class,

                DayScheduleAdapter.class,
                SpeakersAdapter.class,
                OrganizersAdapter.class,

                ScheduleFragment.class,
                DayScheduleFragment.class,
                TalkFragment.class,

                SpeakersListFragment.class,
                SpeakerFragment.class,

                OrganizersFragment.class,

                VenuesRootFragment.class,
                VenueFragment.class,

                RepositoryImpl.class,
                Storage.class
        },
        library = true
)
public class MainModule implements DaggerModule {

    private final Context appContext;

    public MainModule(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Provides
    @Singleton
    EventBus provideBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    Repository provideRepository() {
        return RepositoryImpl.getInstance();
    }

    @Provides
    Context provideContext() {
        return appContext;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        OkHttpClient client = new OkHttpClient();
        client.setProtocols(Collections.singletonList(Protocol.HTTP_1_1));

        return new Picasso.Builder(appContext)
                .downloader(new OkHttpDownloader(client))
                .build();
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(appContext);
    }

    @Provides
    OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient httpClient = new OkHttpClient();
        HttpClientConfig.setCacheInterceptor(httpClient);
        HttpClientConfig.setCache(context, httpClient);
        return httpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    DataFetchService provideService(Retrofit retrofit) {
        return retrofit.create(DataFetchService.class);
    }
}
