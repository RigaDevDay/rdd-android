package lv.rigadevday.android.ui.tabs

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


class TabActivity : BaseActivity() {

    override val layoutId = R.layout.activity_tab
    override val contentFrameId = R.id.tabs_content_container

    private val scheduleFragment: Fragment by lazy { MyScheduleFragment() }
    private val speakersFragment: Fragment by lazy { SpeakerListFragment() }
    private val venuesFragment: Fragment by lazy { VenuesFragment() }
    private val partnersFragment: Fragment by lazy { PartnersFragment() }

    override fun inject() {}

    override fun viewReady() {
        tabs_buttons.setOnNavigationItemSelectedListener(tabClickListener)
        tabs_buttons.menu.findItem(R.id.action_tab_schedule).isChecked = true
        setFragment(scheduleFragment)
    }

    val tabClickListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val nextFragment: Fragment = when (item.itemId) {
            R.id.action_tab_schedule -> scheduleFragment
            R.id.action_tab_speakers -> speakersFragment
            R.id.action_tab_venues -> venuesFragment
            else -> partnersFragment
        }
        setFragment(nextFragment)
        true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_twitter -> {
                openTwitter()
                true
            }
            R.id.action_licences -> {
                openLicencesActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
