package lv.rigadevday.android.ui.speakers.speaker

import kotlinx.android.synthetic.main.activity_speaker.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.ui.EXTRA_SPEAKER_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.logE
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SpeakerActivity : BaseActivity() {

    @Inject lateinit var repo: Repository

    override val layoutId = R.layout.activity_speaker
    override val contentFrameId = -1

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        val speakerId = intent.extras.getInt(EXTRA_SPEAKER_ID, -1)

        repo.speaker(speakerId).subscribe(
            { renderSpeaker(it) },
            { error ->
                speaker_name.showMessage(R.string.error_message)
                finish()
            }
        )
    }

    private fun renderSpeaker(speaker: Speaker) {
        speaker_name.text = speaker.name

        speaker.logE()
    }

}
