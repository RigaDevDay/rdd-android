package lv.rigadevday.android.utils;

import android.app.Application;

import com.bettervectordrawable.Convention;
import com.bettervectordrawable.VectorDrawableCompat;

import java.util.Collections;
import java.util.List;

import dagger.ObjectGraph;
import lv.rigadevday.android.R;
import lv.rigadevday.android.dagger.DaggerModule;
import lv.rigadevday.android.dagger.MainModule;

public class BaseApplication extends Application {

    private static ObjectGraph objectGraph;

    public static <T> void inject(T instance) {
        if (objectGraph != null) objectGraph.inject(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initVectors();

        DaggerModule[] modules = getModules();
        objectGraph = ObjectGraph.create(modules);
    }

    public DaggerModule[] getModules() {
        List<DaggerModule> modules = Collections.<DaggerModule>singletonList(
                new MainModule(this)
        );
        return modules.toArray(new DaggerModule[modules.size()]);
    }

    private void initVectors() {
        int[] ids = VectorDrawableCompat.findVectorResourceIdsByConvention(
                getResources(),
                R.drawable.class,
                Convention.RESOURCE_NAME_HAS_VECTOR_PREFIX);
        VectorDrawableCompat.enableResourceInterceptionFor(false, getResources(), ids);
    }

}
