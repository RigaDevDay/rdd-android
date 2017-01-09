package lv.rigadevday.android.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.ui.licences.LicencesActivity;
import lv.rigadevday.android.ui.navigation.OpenLicencesScreen;
import lv.rigadevday.android.ui.navigation.OpenSpeakerScreen;
import lv.rigadevday.android.ui.navigation.OpenTalkScreen;
import lv.rigadevday.android.ui.navigation.OpenWeb;
import lv.rigadevday.android.ui.speakers.speaker.SpeakerActivity;
import lv.rigadevday.android.ui.speakers.speaker.SpeakerFragment;
import lv.rigadevday.android.ui.talk.TalkActivity;
import lv.rigadevday.android.ui.talk.TalkFragment;
import lv.rigadevday.android.utils.BaseApplication;
import lv.rigadevday.android.utils.Utils;

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
        BaseApplication.Companion.inject(this);
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

    public void onEvent(final OpenTalkScreen event) {
        Intent i = new Intent(this, TalkActivity.class);
        i.putExtra(TalkFragment.EXTRA_DAY, event.day);
        i.putExtra(TalkFragment.EXTRA_TIME, event.time);
        i.putExtra(TalkFragment.EXTRA_INDEX, event.index);
        startActivity(i);
    }

    public void onEvent(final OpenSpeakerScreen event) {
        Intent i = new Intent(this, SpeakerActivity.class);
        i.putExtra(SpeakerFragment.SPEAKER_ID, event.id);
        startActivity(i);
    }

    public void onEvent(final OpenLicencesScreen event) {
        startActivity(new Intent(this, LicencesActivity.class));
    }

    public void onEvent(final OpenWeb event) {
        Utils.goToWeb(this, event.url);
    }

}
