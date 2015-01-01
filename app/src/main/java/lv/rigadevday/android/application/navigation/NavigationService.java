package lv.rigadevday.android.application.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.bookmark.BookmarkFragment;
import lv.rigadevday.android.ui.organizers.OrganizerFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.speakers.SpeakersFragment;
import lv.rigadevday.android.ui.talks.TalkFragment;

public class NavigationService {

    @Inject
    public NavigationService() {

    }

    public List<NavigationOption> getDrawerNavigationOptions() {
        return new ArrayList<>(Arrays.asList(
                new NavigationOption(R.string.schedule, R.drawable.icon_menu_schedule, ScheduleFragment.class),
                new NavigationOption(R.string.speakers, R.drawable.icon_menu_speakers, SpeakersFragment.class),
                new NavigationOption(R.string.talks, R.drawable.icon_menu_talks, TalkFragment.class),
                new NavigationOption(R.string.bookmarks, R.drawable.icon_menu_bookmark, BookmarkFragment.class),
                new NavigationOption(R.string.organizers, R.drawable.icon_menu_organizers, OrganizerFragment.class)
        ));
    }
}
