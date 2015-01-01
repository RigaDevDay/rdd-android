package lv.rigadevday.android.ui.talks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnItemClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.SpeakerPresentation;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.ui.custom.BookmarkSnackBarDisplayFunction;
import lv.rigadevday.android.ui.details.ProfileFragment;

public class TalkFragment extends BaseFragment {

    @Inject
    LayoutInflater inflater;

    @InjectView(R.id.talks_list)
    ListView talksList;


    TalkListAdapter adapter;

    @Override
    protected int contentViewId() {
        return R.layout.talks_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        BookmarkSnackBarDisplayFunction function = new BookmarkSnackBarDisplayFunction(getActivity(), talksList);
        adapter = new TalkListAdapter(getActivity(), inflater, function);
        talksList.setAdapter(adapter);
    }

    @OnItemClick(R.id.talks_list)
    public void onTalkItemClick(int position) {
        Presentation presentation = adapter.getItem(position);

        ProfileFragment profileFragment = FragmentFactory.create(ProfileFragment.class);

        List<SpeakerPresentation> sp = SpeakerPresentation.getByPresentation(presentation.getId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("speaker", sp.get(0).getSpeaker());
        profileFragment.setArguments(bundle);

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, profileFragment, profileFragment.getClass().getName())
                .addToBackStack(null)
                .commit();
    }
}