package lv.rigadevday.android.ui.venues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.utils.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class VenueFragment extends BaseFragment {

    private static final String EXTRA_TITLE = "venue_title";

    @Bind(R.id.venue_name)
    protected TextView name;
    @Bind(R.id.venue_address)
    protected TextView address;
    @Bind(R.id.venue_address_button)
    protected View addressButton;
    @Bind(R.id.venue_web_page_link)
    protected TextView webLink;

    @Bind(R.id.venue_email_layout)
    protected LinearLayout emailLayout;
    @Bind(R.id.venue_email_link)
    protected TextView emailLink;

    @Bind(R.id.venue_wifi_layout)
    protected LinearLayout wifiPassLayout;
    @Bind(R.id.venue_wifi)
    protected TextView wifiPass;

    @Bind(R.id.venue_description)
    protected TextView description;


    public static Fragment newInstance(String string) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, string);

        VenueFragment fragment = new VenueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_venue;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        dataFetchSubscription = repository.getVenue(getArguments().getString(EXTRA_TITLE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        venue -> {
                            name.setText(venue.name);
                            address.setText(venue.address);
                            addressButton.setOnClickListener(v -> Utils.goToMaps(getContext(), venue.coordinates));

                            webLink.setText(venue.web);
                            webLink.setOnClickListener(v -> Utils.goToWeb(getContext(), venue.web));

                            if (Utils.isNullOrEmpty(venue.email)) {
                                emailLayout.setVisibility(View.GONE);
                            } else {
                                emailLink.setText(venue.email);
                                emailLink.setOnClickListener(v -> Utils.goToMail(getContext(), venue.email));
                            }

                            if (Utils.isNullOrEmpty(venue.wifiPass)) {
                                wifiPassLayout.setVisibility(View.GONE);
                            } else {
                                wifiPass.setText(venue.wifiPass);
                            }

                            description.setText(Html.fromHtml(venue.description));
                        },
                        Throwable::printStackTrace
                );
    }

}
