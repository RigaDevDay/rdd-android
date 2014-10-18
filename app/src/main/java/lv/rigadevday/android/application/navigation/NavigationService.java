package lv.rigadevday.android.application.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.agenda.AgendaFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.social.SocialFragment;
import lv.rigadevday.android.ui.speakers.SpeakersFragment;
import lv.rigadevday.android.ui.venue.VenueFragment;

public class NavigationService {

    @Inject
    public NavigationService (){

    }

    public List<NavigationOption> getDrawerNavigationOptions() {
        return new ArrayList<NavigationOption>(Arrays.asList(
            new NavigationOption(R.string.agenda, R.drawable.ic_launcher, AgendaFragment.class),
            new NavigationOption(R.string.schedule, R.drawable.ic_launcher, ScheduleFragment.class),
            new NavigationOption(R.string.social, R.drawable.ic_launcher, SocialFragment.class),
            new NavigationOption(R.string.speakers, R.drawable.ic_launcher, SpeakersFragment.class),
            new NavigationOption(R.string.venue, R.drawable.ic_launcher, VenueFragment.class)
        ));
    }
}
