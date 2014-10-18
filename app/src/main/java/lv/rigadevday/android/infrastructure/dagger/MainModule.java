package lv.rigadevday.android.infrastructure.dagger;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.ui.MainActivity;
import lv.rigadevday.android.ui.MainActivityPresenter;
import lv.rigadevday.android.ui.agenda.AgendaFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.social.SocialFragment;
import lv.rigadevday.android.ui.speakers.SpeakersFragment;
import lv.rigadevday.android.ui.venue.VenueFragment;

@Module(
        injects = {
                BaseApplication.class,
                MainActivity.class,

                ScheduleFragment.class,
                AgendaFragment.class,
                SocialFragment.class,
                SpeakersFragment.class,
                VenueFragment.class
        }
)
public class MainModule implements DaggerModule {

    private final Context appContext;

    public MainModule(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Provides @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    Context provideContext() {
        return appContext;
    }
}
