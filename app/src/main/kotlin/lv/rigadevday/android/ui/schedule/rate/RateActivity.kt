package lv.rigadevday.android.ui.schedule.rate

import kotlinx.android.synthetic.main.activity_rate.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.model.schedule.Rating
import lv.rigadevday.android.ui.EXTRA_SESSION_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.biFunction
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class RateActivity : BaseActivity() {

    @Inject lateinit var repo: Repository

    override val layoutId = R.layout.activity_rate

    override fun inject() = BaseApp.graph.inject(this)

    override fun viewReady() {
        val sessionId = intent.extras.getInt(EXTRA_SESSION_ID)

        dataFetchSubscription = repo.session(sessionId)
            .zipWith(repo.rating(sessionId), biFunction { session, rating ->
                session.also { it.rating = rating }
            })
            .subscribe(
                { session ->
                    rate_background.setOnClickListener { finish() }

                    rate_session_header.setBackgroundColor(session.color)
                    rate_session_title.text = session.title

                    session.speakerObjects.first().let {
                        rate_session_speaker.text = it.name
                        rate_session_speaker.setOnClickListener { it.context.openSpeakerActivity(it.id) }
                    }

                    rate_session_content_stars.progress = session.rating.qualityOfContent
                    rate_session_speaker_stars.progress = session.rating.speakerPerformance
                    rate_session_comment_input.setText(session.rating.comment)
                    rate_session_comment_input.setSelection(session.rating.comment.length)

                    rate_session_submit.setOnClickListener {
                        repo.saveRating(sessionId, Rating(
                            comment = rate_session_comment_input.text.trim().toString(),
                            qualityOfContent = rate_session_content_stars.progress,
                            speakerPerformance = rate_session_speaker_stars.progress
                        ))
                        finish()
                    }
                },
                { e ->
                    e.printStackTrace()
                    rate_session_header.showMessage(R.string.error_message)
                }
            )
    }

}
