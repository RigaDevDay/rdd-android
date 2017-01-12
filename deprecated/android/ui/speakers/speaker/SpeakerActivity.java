package lv.rigadevday.android.ui.speakers.speaker;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseActivity;

/**
 */
public class SpeakerActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_frame_no_toolbar;
    }

    @Override
    public void initializeScreen() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_content_frame, SpeakerFragment.newInstance(getIntent().getExtras()))
                .commit();
    }

    public void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
