package lv.rigadevday.android.ui.venues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.base.ViewPagerAdapter;
import lv.rigadevday.android.ui.venues.places.AfterpartyVenueFragment;
import lv.rigadevday.android.ui.venues.places.ConferenceVenueFragment;
import lv.rigadevday.android.ui.venues.places.HotelVenueFragment;
import lv.rigadevday.android.ui.venues.places.WorkshopsVenueFragment;

/**
 */
public class VenuesFragment extends BaseFragment {

    @Bind(R.id.venues_tabs)
    TabLayout mTabs;
    @Bind(R.id.venues_pager)
    ViewPager mPager;

    @Override
    protected int contentViewId() {
        return R.layout.fragment_venues_root;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(ConferenceVenueFragment.newInstance(), getString(R.string.venue_conference));
        adapter.addFragment(WorkshopsVenueFragment.newInstance(), getString(R.string.venue_workshops));
        adapter.addFragment(AfterpartyVenueFragment.newInstance(), getString(R.string.venue_afterparty));
        adapter.addFragment(HotelVenueFragment.newInstance(), getString(R.string.venue_hotel));

        mPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mPager);
    }

}