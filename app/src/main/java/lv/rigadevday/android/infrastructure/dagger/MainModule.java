package lv.rigadevday.android.infrastructure.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.v2.repository.InMemoryStorage;
import lv.rigadevday.android.v2.repository.Repository;
import lv.rigadevday.android.v2.ui.schedule.day.DayScheduleFragment;
import lv.rigadevday.android.v2.ui.usefulstuff.UsefulStuffFragment;
import lv.rigadevday.android.v2.ui.base.BaseActivity;
import lv.rigadevday.android.v2.ui.drawer.DrawerActivity;
import lv.rigadevday.android.v2.ui.favorites.FavoritesFragment;
import lv.rigadevday.android.v2.ui.organizers.OrganizersFragment;
import lv.rigadevday.android.v2.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.v2.ui.sponsors.SponsorsFragment;
import lv.rigadevday.android.v2.ui.talk.TalkActivity;
import lv.rigadevday.android.v2.ui.talk.TalkFragment;

@Module(
        injects = {
                BaseApplication.class,

                BaseActivity.class,
                DrawerActivity.class,
                TalkActivity.class,

                ScheduleFragment.class,
                DayScheduleFragment.class,
                FavoritesFragment.class,
                SponsorsFragment.class,
                OrganizersFragment.class,
                UsefulStuffFragment.class,
                TalkFragment.class,
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
        return InMemoryStorage.getInstance();
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
