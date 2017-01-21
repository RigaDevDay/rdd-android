package lv.rigadevday.android.ui.tabs

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_tab.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.schedule.MyScheduleFragment
import lv.rigadevday.android.ui.speakers.SpeakerListFragment


class TabActivity : BaseActivity() {

    override val layoutId = R.layout.activity_tab
    override val contentFrameId = R.id.tabs_content_container

    private val scheduleFragment: Fragment by lazy { MyScheduleFragment() }
    private val speakersFragment: Fragment by lazy { SpeakerListFragment() }
    private val venuesFragment: Fragment by lazy { PlaceholderFragment.newInstance("venues") }
    private val organizersFragment: Fragment by lazy { PlaceholderFragment.newInstance("organizers") }

    override fun inject() {}

    override fun viewReady() {
        tabs_buttons.setOnNavigationItemSelectedListener(tabClickListener)
        tabs_buttons.menu.findItem(R.id.action_tab_speakers).isChecked = true
        setFragment(scheduleFragment)
    }

    val tabClickListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val nextFragment: Fragment = when (item.itemId) {
            R.id.action_tab_schedule -> scheduleFragment
            R.id.action_tab_speakers -> speakersFragment
            R.id.action_tab_venues -> venuesFragment
            else -> organizersFragment
        }
        setFragment(nextFragment)
        true
    }
}
