package lv.rigadevday.android.ui.venues.places;

import android.support.v4.app.Fragment;

import butterknife.OnClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.utils.Utils;

/**
 */
public class AfterpartyVenueFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new AfterpartyVenueFragment();
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_venue_afterparty;
    }

    @OnClick(R.id.venue_address_button)
    public void onAddressButtonClick() {
        Utils.goToMaps(getContext(), getString(R.string.venue_afterparty_coordinates));
    }

    @OnClick(R.id.venue_web_page_link)
    public void onWebLinkClick() {
        Utils.goToWeb(getContext(), getString(R.string.venue_afterparty_web));
    }


}
