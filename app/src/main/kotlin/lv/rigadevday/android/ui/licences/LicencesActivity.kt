package lv.rigadevday.android.ui.licences

import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_licences.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseActivity

/**
 */
class LicencesActivity : BaseActivity() {

    override val layoutId = R.layout.activity_licences

    override fun inject() {}

    override fun viewReady() {
        setupActionBar(R.string.licenses_title)

        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        licences_webview.loadUrl("file:///android_asset/licenses.html")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }
}
