package lv.rigadevday.android.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.ui.BaseFragment;

public class ProfileSpeechFragment extends BaseFragment {

    @Inject
    Context context;

    @Inject
    LayoutInflater inflater;

    @Bind(R.id.speech_list)
    ListView speechList;

    @Override
    protected int contentViewId() {
        return R.layout.profile_speech_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        Speaker speaker = (Speaker) getArguments().get("speaker");
        List<Presentation> presentations = speaker.getPresentations();

        SpeechListAdapter speechListAdapter = new SpeechListAdapter(context, inflater, presentations);
        speechList.setAdapter(speechListAdapter);
    }
}