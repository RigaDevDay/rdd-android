package lv.rigadevday.android.ui.schedule.rate

import kotlinx.android.synthetic.main.activity_rate.*
import kotlinx.android.synthetic.main.activity_session_details.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.EXTRA_SESSION_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class RateActivity : BaseActivity() {

    @Inject lateinit var repo: Repository

    override val layoutId = R.layout.activity_rate

    override fun inject() = BaseApp.graph.inject(this)

    override fun viewReady() {

        val sessionId = intent.extras.getInt(EXTRA_SESSION_ID)

        dataFetchSubscription = repo.session(sessionId).subscribe(
            { session ->
                rate_session_header.setBackgroundColor(session.color)
                rate_session_title.text = session.title

                val speaker = session.speakerObjects.first()
                rate_session_speaker.text = speaker.name
                rate_session_speaker.setOnClickListener { it.context.openSpeakerActivity(speaker.id) }

            },
            { session_details_description.showMessage(R.string.error_message) }
        )

    }

}
