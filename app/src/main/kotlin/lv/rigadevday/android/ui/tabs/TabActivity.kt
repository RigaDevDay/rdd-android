package lv.rigadevday.android.ui.tabs

import android.content.res.Configuration
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_tab.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openLicencesActivity
import lv.rigadevday.android.ui.openTwitter
import lv.rigadevday.android.ui.partners.PartnersFragment
import lv.rigadevday.android.ui.schedule.MyScheduleFragment
import lv.rigadevday.android.ui.speakers.SpeakerListFragment
import lv.rigadevday.android.ui.venues.VenuesFragment
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.auth.AuthStorage
import javax.inject.Inject


class TabActivity : BaseActivity() {

    @Inject lateinit var authStorage: AuthStorage

    override val layoutId = R.layout.activity_tab
    override val contentFrameId = R.id.tabs_content_container
    override val ignoreUiUpdates = true

    private val scheduleFragment: Fragment by lazy { MyScheduleFragment() }
    private val speakersFragment: Fragment by lazy { SpeakerListFragment() }
    private val venuesFragment: Fragment by lazy { VenuesFragment() }
    private val partnersFragment: Fragment by lazy { PartnersFragment() }

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        tabs_buttons.setOnNavigationItemSelectedListener(tabClickListener)
        tabs_buttons.menu.findItem(R.id.action_tab_schedule).isChecked = true
        scheduleFragment.setAsMain()
    }

    private val tabClickListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        item.itemId.toFragment().setAsMain()
        true
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        tabs_buttons.selectedItemId.toFragment().setAsMain()
    }

    private fun Int.toFragment(): Fragment = when (this) {
        R.id.action_tab_schedule -> scheduleFragment
        R.id.action_tab_speakers -> speakersFragment
        R.id.action_tab_venues -> venuesFragment
        else -> partnersFragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuFile =
            if (authStorage.hasLogin) R.menu.menu_main_logout
            else R.menu.menu_main_login

        menuInflater.inflate(menuFile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_twitter -> openTwitter()
            R.id.action_login -> loginWrapper.logIn(this)
            R.id.action_logout -> loginWrapper.logOut()
            R.id.action_licences -> openLicencesActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun refreshLoginState() {
        invalidateOptionsMenu()
    }
}
