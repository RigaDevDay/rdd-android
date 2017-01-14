package lv.rigadevday.android.ui.speakers.speaker

import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseActivity

class SpeakerActivity : BaseActivity() {

    override val layoutId = R.layout.activity_content_frame_no_toolbar
    override val contentFrameId = R.id.activity_content_frame

    override fun viewReady() {
        setFragment(SpeakerFragment.newInstance(intent.extras))
    }

}
