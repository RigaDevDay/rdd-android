package lv.rigadevday.android.ui.schedule.details

import android.app.Activity
import android.os.Build
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
import lv.rigadevday.android.utils.*
import lv.rigadevday.android.utils.auth.AuthStorage
import java.util.*
import javax.inject.Inject

class SessionDetailsActivity : BaseActivity() {

    @Inject lateinit var storage: SessionStorage
    @Inject lateinit var authStorage: AuthStorage

    override val layoutId = R.layout.activity_session_details

    private var sessionId: Int = 0

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun refreshLoginState() {
        updateLoginRateButton()
    }

    override fun viewReady() {
        sessionId = intent.extras.getInt(EXTRA_SESSION_ID)
        val skippable = intent.extras.getBoolean(EXTRA_SESSION_SKIPPABLE, false)

        setupActionBar("")
        homeAsUp()

        updateLoginRateButton()

        if (skippable) with(session_to_schedule) {
            show()
            setOnClickListener {
                setResult(Activity.RESULT_FIRST_USER)
                finish()
            }
        }

        dataFetchSubscription = repo.session(sessionId)
            .subscribe(
                { session ->
                    setToolbarColor(session.color)
                    session_details_title.text = session.title

                    val speaker = session.speakerObjects.first()
                    session_details_speaker.text = speaker.name
                    session_details_speaker.setOnClickListener { it.context.openSpeakerActivity(speaker.id) }

                    session_details_tags.text = session.complexityAndTags
                    session_details_description.text = session.description.fromHtml()

                    updateBookmarkIcon(session, sessionId)
                },
                {
                    if (it is NoSuchElementException) {
                        session_details_description.showMessage(R.string.session_cancelled)
                    } else {
                        session_details_description.showMessage(R.string.error_message)
                    }
                    finish()
                }
            )
    }

    private fun setToolbarColor(color: Int) {
        toolbar.setBackgroundColor(color)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color.darker()
        }
    }

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

}
