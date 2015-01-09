package lv.rigadevday.android.ui.bookmark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import lv.rigadevday.android.ui.details.ProfileFragment;

public class BookmarkFragment extends BaseFragment {

    @InjectView(R.id.bookmark_list)
    ListView bookmarkList;

    @InjectView(R.id.empty_bookmarks_message_holder)
    ViewGroup messageHolder;

    @Inject
    Context context;

    @Inject
    LayoutInflater layoutInflater;
    private BookmarkListAdapter adapter;

    @Override
    protected int contentViewId() {
        return R.layout.bookmark_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new BookmarkListAdapter(context, layoutInflater, Presentation.getBookmarked());
        bookmarkList.setAdapter(adapter);

        int count = adapter.getCount();
        messageHolder.setVisibility(count == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @OnItemClick(R.id.bookmark_list)
    public void onBookmarkItemClick(int position) {
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