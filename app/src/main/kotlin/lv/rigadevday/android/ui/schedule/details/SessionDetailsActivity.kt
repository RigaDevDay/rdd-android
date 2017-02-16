package lv.rigadevday.android.ui.schedule.details

import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_session_details.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.ui.EXTRA_SESSION_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.fromHtml
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SessionDetailsActivity : BaseActivity() {

    @Inject lateinit var repo: Repository
    @Inject lateinit var storage: SessionStorage

    override val layoutId = R.layout.activity_session_details

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        val sessionId = intent.extras.getInt(EXTRA_SESSION_ID)

        session_close.setOnClickListener { finish() }
        session_background.setOnClickListener { finish() }

        dataFetchSubscription = repo.session(sessionId)
            .zipWith(getTimeslot(sessionId), BiFunction { session: Session, timeslot: TimeDataPair ->
                session.time = timeslot.time
                session.date = timeslot.date
                return@BiFunction session
            })
            .subscribe(
                { session ->
                    session_details_header.setBackgroundColor(session.color)
                    session_details_title.text = session.title

                    val speaker = session.speakerObjects.first()
                    session_details_speaker.text = speaker.name
                    session_details_speaker.setOnClickListener { it.context.openSpeakerActivity(speaker.id) }

                    session_details_tags.text = session.complexityAndTags
                    session_details_description.text = session.description.fromHtml()

                    updateBookmarkIcon(session, sessionId)
                },
                { session_details_description.showMessage(R.string.error_message) }
            )
    }

    private fun updateBookmarkIcon(session: Session, sessionId: Int) {
        val savedSessionId = storage.getSessionId(session.time, session.date)
        if (savedSessionId != null && savedSessionId == sessionId) {
            session_details_bookmark.setImageResource(R.drawable.vector_remove_bookmark)
            session_details_bookmark.setOnClickListener {
                storage.removeSession(session.time, session.date)
                updateBookmarkIcon(session, sessionId)
            }
        } else {
            session_details_bookmark.setImageResource(R.drawable.vector_add_bookmark)
            session_details_bookmark.setOnClickListener {
                storage.saveSession(session.time, session.date, sessionId)
                updateBookmarkIcon(session, sessionId)
            }
        }
    }

    private fun getTimeslot(sessionId: Int) = repo.schedule().flatMapMaybe { day ->
        day.timeslots.firstOrNull { it.sessionIds.contains(sessionId) }
            ?.let { Maybe.just(TimeDataPair(it.startTime, day.date)) }
            ?: Maybe.empty()
    }.firstElement()

    private data class TimeDataPair(
        val time: String,
        val date: String
    )

}
