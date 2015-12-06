package lv.rigadevday.android.infrastructure.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.ui.MainActivity;
import lv.rigadevday.android.ui.bookmark.BookmarkFragment;
import lv.rigadevday.android.ui.details.ProfileAboutFragment;
import lv.rigadevday.android.ui.details.ProfileFragment;
import lv.rigadevday.android.ui.details.ProfileSpeechFragment;
import lv.rigadevday.android.ui.organizers.OrganizerFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.schedule.ScheduleTrackFragment;
import lv.rigadevday.android.ui.talks.TalkFragment;

@Module(
        injects = {
                BaseApplication.class,
                MainActivity.class,

                TalkFragment.class,
                ScheduleFragment.class,
                ScheduleTrackFragment.class,
                OrganizerFragment.class,
                BookmarkFragment.class,

                ProfileFragment.class,
                ProfileAboutFragment.class,
                ProfileSpeechFragment.class
        }
)
public class MainModule implements DaggerModule {

    private final Context appContext;

    public MainModule(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    Context provideContext() {
        return appContext;
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(appContext);
    }
}
