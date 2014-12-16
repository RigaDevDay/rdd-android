package lv.rigadevday.android.ui.speakers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.ui.custom.view.parallax.ParallaxListItem;
import lv.rigadevday.android.ui.custom.view.parallax.ParallaxListView;
import lv.rigadevday.android.ui.details.ProfileFragment;

public class SpeakersFragment extends BaseFragment {

    @InjectView(R.id.speaker_list)
    ParallaxListView parallaxListView;

    @Override
    protected int contentViewId() {
        return R.layout.speakers_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemClickFunction itemClickFunction = new ItemClickFunction(this.getActivity());
        List<ParallaxListItem> items = Lists.newArrayList();
        items.addAll(Speaker.getAll());

        parallaxListView.setDisplayItems(items);
        parallaxListView.setOnItemClickListener(itemClickFunction);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private static class ItemClickFunction implements Function<Speaker, Void> {

        private FragmentActivity activity;

        private ItemClickFunction(FragmentActivity activity) {
            this.activity = activity;
        }

        @Override
        public Void apply(Speaker speaker) {

            ProfileFragment profileFragment = FragmentFactory.create(ProfileFragment.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("speaker", speaker);
            profileFragment.setArguments(bundle);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, profileFragment, profileFragment.getClass().getName())
                    .commit();

            return null;
        }
    }
}
