package lv.rigadevday.android.ui.venues

import android.view.View
import kotlinx.android.synthetic.main.fragment_venues_root.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.base.ViewPagerAdapter
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage

class VenuesFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_venues_root

    private lateinit var pageAdapter: ViewPagerAdapter

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        setupActionBar(R.string.venues_title)

        pageAdapter = ViewPagerAdapter(childFragmentManager)

        dataFetchSubscription = repo.venues().toList().subscribe(
            {
                it.forEachIndexed { i, venue ->
                    pageAdapter.addFragment(VenueDetailsFragment.newInstance(i), venue.name)
                }

                with(view.venues_pager) {
                    offscreenPageLimit = 2
                    adapter = pageAdapter
                    view.venues_tabs.setupWithViewPager(this)
                }
            },
            { view.showMessage(R.string.error_message) }
        )
    }
}
