package lv.rigadevday.android;

import android.content.Context;

import com.activeandroid.app.Application;
import com.bettervectordrawable.Convention;
import com.bettervectordrawable.VectorDrawableCompat;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import lv.rigadevday.android.infrastructure.dagger.DaggerModule;
import lv.rigadevday.android.infrastructure.dagger.MainModule;

public class BaseApplication extends Application {

    private static ObjectGraph objectGraph;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initVectors();

        appContext = getApplicationContext();

        DaggerModule[] modules = getModules();
        objectGraph = ObjectGraph.create(modules);
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

    private void initVectors() {
        int[] ids = VectorDrawableCompat.findVectorResourceIdsByConvention(
                getResources(),
                R.drawable.class,
                Convention.RESOURCE_NAME_HAS_VECTOR_PREFIX);
        VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);
    }

    public static Context getContext() {
        return appContext;
    }
}
