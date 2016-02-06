package lv.rigadevday.android.ui.talk;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseActivity;

/**
 */
public class TalkActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_frame;
    }

    @Override
    public void initializeScreen() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_content_frame, TalkFragment.newInstance(getIntent().getExtras()))
                .commit();
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
