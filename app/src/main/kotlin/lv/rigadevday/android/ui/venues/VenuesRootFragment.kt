package lv.rigadevday.android.ui.venues

import android.view.View
import kotlinx.android.synthetic.main.fragment_venues_root.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.base.ViewPagerAdapter
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage

class VenuesRootFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_venues_root

    private var adapter: ViewPagerAdapter? = null

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        setupActionBar(R.string.venues_title)

        adapter = ViewPagerAdapter(childFragmentManager)

        var index = 0

        dataFetchSubscription = repo.venues().subscribe(
                {
                    adapter?.addFragment(VenueFragment.newInstance(index), it.name)
                    index++
                },
                { view.showMessage(R.string.error_message) },
                {
                    view.venues_pager.offscreenPageLimit = 2
                    view.venues_pager.adapter = adapter
                    view.venues_tabs.setupWithViewPager(view.venues_pager)
                }
        )
    }
}