package lv.rigadevday.android.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tab.*
import lv.rigadevday.android.R


class TabActivity : AppCompatActivity() {

    private val scheduleFragment: Fragment by lazy { BasicFragment.newInstance("schedule") }
    private val speakersFragment: Fragment by lazy { BasicFragment.newInstance("speakers") }
    private val venuesFragment: Fragment by lazy { BasicFragment.newInstance("venues") }
    private val organizersFragment: Fragment by lazy { BasicFragment.newInstance("organizers") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        tabs_buttons.setOnNavigationItemSelectedListener(tabClickListener)
        tabs_buttons.menu.findItem(R.id.action_tab_speakers).isChecked = true

        setFragment(speakersFragment)
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

    private fun setFragment(nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.tabs_content_container, nextFragment, nextFragment.tag)
            .commit()
    }
}
