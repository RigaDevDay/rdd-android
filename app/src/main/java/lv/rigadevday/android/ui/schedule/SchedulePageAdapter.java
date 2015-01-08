package lv.rigadevday.android.ui.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.common.collect.Lists;

import java.util.List;

import lv.rigadevday.android.domain.Track;

public class SchedulePageAdapter extends FragmentStatePagerAdapter {

    private final List<ScheduleTrackFragment> fragments = Lists.newArrayList();
    private final List<Track> tracks;

    public SchedulePageAdapter(FragmentManager fm, List<Track> tracks) {
        super(fm);
        this.tracks = tracks;

        for (Track track : tracks) {
            fragments.add(ScheduleTrackFragment.newInstance(track.getId()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tracks.get(position).getTrackName();
    }
}
