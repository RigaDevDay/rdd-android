package lv.rigadevday.android.ui.venues;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.base.ViewPagerAdapter;

/**
 */
public class VenuesRootFragment extends BaseFragment {

    @Bind(R.id.venues_tabs)
    protected TabLayout mTabs;
    @Bind(R.id.venues_pager)
    protected ViewPager mPager;

    @Override
    protected int contentViewId() {
        return R.layout.fragment_venues_root;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        String conference = getString(R.string.venue_conference);
        String workshops = getString(R.string.venue_workshops);
        String afterparty = getString(R.string.venue_afterparty);
        String hotel = getString(R.string.venue_hotel);

        adapter.addFragment(VenueFragment.newInstance(conference), conference);
        adapter.addFragment(VenueFragment.newInstance(workshops), workshops);
        adapter.addFragment(VenueFragment.newInstance(afterparty), afterparty);
        adapter.addFragment(VenueFragment.newInstance(hotel), hotel);

        mPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mPager);
    }

}