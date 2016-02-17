package lv.rigadevday.android.ui.licences;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseActivity;

/**
 */
public class LicencesActivity extends BaseActivity {

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.licences_webview)
    protected WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_licences;
    }

    @Override
    public void initializeScreen() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        webview.loadUrl("file:///android_asset/licenses.html");
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
