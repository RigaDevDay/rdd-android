package lv.rigadevday.android.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_my_schedule.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.base.ViewPagerAdapter
import lv.rigadevday.android.ui.openSessionsActivity
import lv.rigadevday.android.ui.schedule.day.DayScheduleFragment
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage

class MyScheduleFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_my_schedule

    private var adapter: ViewPagerAdapter? = null

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun viewReady(view: View) {
        setupActionBar(R.string.schedule_title)
        adapter = ViewPagerAdapter(childFragmentManager)

        dataFetchSubscription = repo.schedule().subscribe(
            { adapter?.addFragment(DayScheduleFragment.newInstance(it.date, it.dateReadable), it.dateReadable) },
            { view.showMessage(R.string.error_message) },
            {
                view.schedule_pager.offscreenPageLimit = 2
                view.schedule_pager.adapter = adapter
                view.schedule_tabs.setupWithViewPager(view.schedule_pager)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_schedule, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_all_sessions -> {
                openSessionsActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
