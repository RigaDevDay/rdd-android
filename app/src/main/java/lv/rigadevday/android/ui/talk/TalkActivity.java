package lv.rigadevday.android.ui.talk;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseActivity;

/**
 */
public class TalkActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_frame_no_toolbar;
    }

    @Override
    public void initializeScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_content_frame, TalkFragment.newInstance(getIntent().getExtras()))
                .commit();
    }

    public void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
