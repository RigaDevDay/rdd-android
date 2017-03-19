package lv.rigadevday.android.ui.schedule.details

import android.app.Activity
import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_session_details.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.ui.EXTRA_SESSION_ID
import lv.rigadevday.android.ui.EXTRA_SESSION_SKIPPABLE
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openRateSessionActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.auth.AuthStorage
import lv.rigadevday.android.utils.fromHtml
import lv.rigadevday.android.utils.show
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SessionDetailsActivity : BaseActivity() {

    @Inject lateinit var repo: Repository
    @Inject lateinit var storage: SessionStorage
    @Inject lateinit var authStorage: AuthStorage

    override val layoutId = R.layout.activity_session_details

    private var sessionId : Int = 0

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun refreshLoginState() {
        updateLoginRateButton()
    }

    override fun viewReady() {
        sessionId = intent.extras.getInt(EXTRA_SESSION_ID)
        val skippable = intent.extras.getBoolean(EXTRA_SESSION_SKIPPABLE, false)

        updateLoginRateButton()
        session_background.setOnClickListener { finish() }

        if (skippable) with(session_to_schedule) {
            show()
            setOnClickListener {
                setResult(Activity.RESULT_FIRST_USER)
                finish()
            }
        }

        dataFetchSubscription = repo.session(sessionId)
            .zipWith(getTimeslot(sessionId), biFunction { session, (time, date) ->
                session.time = time
                session.date = date
                return@biFunction session
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

    private fun <T, R> biFunction(function: (T, R) -> T): BiFunction<T, R, T> = BiFunction(function)

    private fun updateLoginRateButton() {
        if (!authStorage.hasLogin) {
            session_login_rate.setText(R.string.session_login_to_rate)
            session_login_rate.setOnClickListener {
                loginWrapper.logIn(this)
            }
        } else {
            session_login_rate.setText(R.string.session_rate)
            session_login_rate.setOnClickListener {
                openRateSessionActivity(sessionId)
            }
        }
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

    private fun getTimeslot(sessionId: Int) = repo.schedule().flatMapMaybe { (date, _, timeslots) ->
        timeslots.firstOrNull { it.sessionIds.contains(sessionId) }
            ?.let { Maybe.just(TimeDataPair(it.startTime, date)) }
            ?: Maybe.empty()
    }.firstElement()

    private data class TimeDataPair(
        val time: String,
        val date: String
    )

}
