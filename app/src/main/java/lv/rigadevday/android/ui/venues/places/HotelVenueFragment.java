package lv.rigadevday.android.ui.venues.places;

import android.support.v4.app.Fragment;

import butterknife.OnClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.utils.Utils;

/**
 */
public class HotelVenueFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new HotelVenueFragment();
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_venue_hotel;
    }

    @OnClick(R.id.venue_address_button)
    public void onAddressButtonClick() {
        Utils.goToMaps(getContext(), getString(R.string.venue_hotel_coordinates));
    }

    @OnClick(R.id.venue_email_link)
    public void onEmailLinkClick() {
        Utils.goToMail(getContext(), getString(R.string.venue_hotel_email));
    }

    @OnClick(R.id.venue_web_page_link)
    public void onWebLinkClick() {
        Utils.goToWeb(getContext(), getString(R.string.venue_hotel_web));
    }


}
