package lv.rigadevday.android.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.BaseFragment;

public class ProfileFragment extends BaseFragment {

    @Inject
    Context context;

    @InjectView(R.id.profile_about_tab_text)
    TextView aboutTextView;

    @InjectView(R.id.profile_speech_tab_text)
    TextView speechTextView;

    @InjectView(R.id.profile_about_tab_line)
    ImageView aboutLine;

    @InjectView(R.id.profile_speech_tab_line)
    ImageView speechLine;

    @InjectView(R.id.profile_name)
    TextView nameTextView;

    @InjectView(R.id.profile_company)
    TextView companyTextView;

    @InjectView(R.id.profile_photo)
    ImageView photoImageView;

    @InjectView(R.id.profile_backstage)
    ImageView backstageImageView;


    private Class<? extends BaseFragment> currentFragment;
    private Speaker speaker;

    @Override
    protected int contentViewId() {
        return R.layout.profile_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        speaker = (Speaker) arguments.get("speaker");

        Picasso.with(context)
                .load(speaker.getImageUrl(context))
                .placeholder(R.drawable.speaker_0)
                .priority(Picasso.Priority.HIGH)
                .skipMemoryCache()
                .into(photoImageView);

        Picasso.with(context)
                .load(speaker.getBackstageImageResource(context))
                .priority(Picasso.Priority.HIGH)
                .skipMemoryCache()
                .into(backstageImageView);


        String name = speaker.getName();
        nameTextView.setText(name);
        String company = speaker.getCompany();
        companyTextView.setText(company);

        aboutTextView.setText(String.format("%s %s", getString(R.string.about), name.split(" ")[0]));

        onSpeechClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.profile_speech_tab)
    public void onSpeechClick() {
        if (!ProfileSpeechFragment.class.equals(currentFragment)) {
            setActive(speechTextView, speechLine);
            setInactive(aboutTextView, aboutLine);
            initContent(ProfileSpeechFragment.class, Transition.LEFT);
        }
    }

    @OnClick(R.id.profile_about_tab)
    public void onAboutClick() {
        if (!ProfileAboutFragment.class.equals(currentFragment)) {
            setActive(aboutTextView, aboutLine);
            setInactive(speechTextView, speechLine);
            initContent(ProfileAboutFragment.class, Transition.RIGHT);
        }
    }

    private void setActive(TextView textView, ImageView imageView) {
        textView.setTextColor(getResources().getColor(R.color.white));
        imageView.setVisibility(View.VISIBLE);
    }

    private void setInactive(TextView textView, ImageView imageView) {
        textView.setTextColor(getResources().getColor(R.color.inactive_white));
        imageView.setVisibility(View.INVISIBLE);
    }

    private void initContent(Class<? extends BaseFragment> fragmentClass, Transition side) {
        currentFragment = fragmentClass;

        BaseFragment fragment = FragmentFactory.create(fragmentClass);
        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable("speaker", speaker);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(side.enter, side.exit);

        transaction.replace(R.id.profile_content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private static enum Transition {
        RIGHT(R.anim.enter_from_right, R.anim.exit_to_left),
        LEFT(R.anim.enter_from_left, R.anim.exit_to_right);
        int enter;
        int exit;

        Transition(int enter, int exit) {
            this.enter = enter;
            this.exit = exit;
        }
    }
}