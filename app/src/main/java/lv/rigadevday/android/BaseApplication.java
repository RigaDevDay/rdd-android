package lv.rigadevday.android;

import com.activeandroid.app.Application;
import com.activeandroid.query.Delete;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import lv.rigadevday.android.domain.Contact;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.SpeakerPresentation;
import lv.rigadevday.android.infrastructure.dagger.DaggerModule;
import lv.rigadevday.android.infrastructure.dagger.MainModule;

public class BaseApplication extends Application {

    private static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerModule[] modules = getModules();
        objectGraph = ObjectGraph.create(modules);

        // TODO: Remove, prototyping.
        new Delete().from(Presentation.class).execute();
        new Delete().from(SpeakerPresentation.class).execute();
        new Delete().from(Contact.class).execute();
        new Delete().from(Speaker.class).execute();

        Mocks.createPresentations(this);
        Mocks.createSpeakers(this);
    }

    public static <T> void inject(T instance) {
        if (objectGraph != null) objectGraph.inject(instance);
    }

    public DaggerModule[] getModules() {
        List<DaggerModule> modules = Arrays.<DaggerModule>asList(
                new MainModule(this)
        );
        return modules.toArray(new DaggerModule[modules.size()]);
    }
}
