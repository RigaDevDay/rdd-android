package lv.rigadevday.android.v2.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.v2.navigation.OpenTalkEvent;
import lv.rigadevday.android.v2.ui.talk.TalkActivity;

/**
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    EventBus bus;

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initializeScreen();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BaseApplication.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initializeScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }


    public void onEvent(final OpenTalkEvent event) {
        startActivity(new Intent(this, TalkActivity.class));
    }
}
