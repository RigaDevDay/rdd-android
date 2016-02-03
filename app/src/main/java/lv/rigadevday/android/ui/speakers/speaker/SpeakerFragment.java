package lv.rigadevday.android.ui.speakers.speaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.utils.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class SpeakerFragment extends BaseFragment {

    @Inject
    Picasso picasso;

    public static final String SPEAKER_ID = "speaker_id";

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.speaker_image)
    protected ImageView image;
    @Bind(R.id.speaker_title_company)
    protected TextView titleCompany;
    @Bind(R.id.speaker_bio)
    protected TextView bio;

    @Bind(R.id.speaker_button_blog)
    protected View buttonBlog;
    @Bind(R.id.speaker_button_twitter)
    protected View buttonTwitter;
    @Bind(R.id.speaker_button_linkedin)
    protected View buttonLinkedin;

    public static Fragment newInstance(Bundle extras) {
        SpeakerFragment fragment = new SpeakerFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_speaker;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SpeakerActivity) getActivity()).setupToolbar(toolbar);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mDataFetch = mRepository.getSpeakers(getArguments().getString(SPEAKER_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(speaker -> {

                    picasso.load(Utils.imagePrefix(speaker.img))
                            .fit()
                            .into(image);

                    toolbar.setTitle(speaker.name);
                    toolbar.setSubtitle(speaker.title);

                    titleCompany.setText(String.format(getString(R.string.speaker_title_company_pattern),
                            speaker.title.trim(), speaker.company.trim()));

                    bio.setText(Html.fromHtml(speaker.description));

                    setupButton(speaker.blog, buttonBlog);
                    setupButton(speaker.twitter, buttonTwitter);
                    setupButton(speaker.linkedin, buttonLinkedin);
                });
    }

    private void setupButton(String url, View button) {
        if (Utils.isNullOrEmpty(url)) {
            button.setVisibility(View.GONE);
        } else {
            button.setOnClickListener(view -> Utils.goToWeb(getContext(), url));
        }
    }
}
