package lv.rigadevday.android.ui.speakers.speaker

import android.os.Bundle
import android.view.View
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.ui.EXTRA_SPEAKER_ID
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.logE
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SpeakerFragment : BaseFragment() {

    @Inject lateinit var repo: Repository

    companion object {
        fun newInstance(args: Bundle): SpeakerFragment {
            return SpeakerFragment().apply { arguments = args }
        }
    }

    override val layoutId = R.layout.fragment_speaker

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        val speakerId = arguments.getInt(EXTRA_SPEAKER_ID, -1)

        repo.speaker(speakerId).subscribe(
            { renderSpeaker(it) },
            { error ->
                view.showMessage(R.string.error_message)
                activity.finish()
            }
        )
    }

    private fun renderSpeaker(speaker: Speaker) {
        speaker.logE()
    }


}
