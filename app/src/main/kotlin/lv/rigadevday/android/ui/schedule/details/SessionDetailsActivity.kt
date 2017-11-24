package lv.rigadevday.android.ui.schedule.details

import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.activity_session_details.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.RemotePrefs
import lv.rigadevday.android.ui.EXTRA_SESSION_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openRateSessionActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.auth.AuthStorage
import lv.rigadevday.android.utils.bindSchedulers
import lv.rigadevday.android.utils.fromHtml
import lv.rigadevday.android.utils.hide
import lv.rigadevday.android.utils.showMessage
import java.util.*
import javax.inject.Inject

class SessionDetailsActivity : BaseActivity() {

    @Inject lateinit var authStorage: AuthStorage
    @Inject lateinit var remotePrefs: RemotePrefs

    override val layoutId = R.layout.activity_session_details

    private var sessionId: Int = 0

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun refreshLoginState() {
        updateLoginRateButton()
        updateBookmarkIcon()
    }

    override fun viewReady() {
        sessionId = intent.extras.getInt(EXTRA_SESSION_ID)

        setupActionBar("")
        homeAsUp()

        updateLoginRateButton()

        dataFetchSubscription = repo.session(sessionId)
            .subscribe(
                { session ->
                    session_details_title.text = session.title

                    val speaker = session.speakerObjects.first()
                    session_details_speaker.text = speaker.name
                    session_details_speaker.setOnClickListener { it.context.openSpeakerActivity(speaker.id) }

                    session_details_tags.text = session.complexityAndTags
                    session_details_description.movementMethod = LinkMovementMethod.getInstance()
                    session_details_description.text = session.description.fromHtml()

                    updateBookmarkIcon()
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

    private fun updateLoginRateButton() = with(session_login_rate) {
        if (!authStorage.hasLogin) {
            setText(R.string.session_login_to_rate)
            setOnClickListener {
                loginWrapper.logIn(this@SessionDetailsActivity)
            }
        } else {
            setText(R.string.session_rate)
            setOnClickListener {

                if (remotePrefs.isRatingAllowed) {
                    openRateSessionActivity(sessionId)
                } else {
                    showMessage(R.string.session_rate_disabled)
                }
            }
        }
    }

    private fun updateBookmarkIcon() {
        if (!authStorage.hasLogin) {
            session_details_bookmark.apply {
                setImageResource(R.drawable.vector_login)
                setOnClickListener {
                    loginWrapper.logIn(this@SessionDetailsActivity)
                    updateBookmarkIcon()
                    updateLoginRateButton()
                }
            }
        } else {
            repo.isSessionBookmarked(sessionId)
                .bindSchedulers()
                .subscribe(
                    { isBookmarked ->
                        session_details_bookmark.apply {
                            if (isBookmarked) {
                                setImageResource(R.drawable.vector_remove_bookmark)
                                setOnClickListener {
                                    repo.removeBookmark(sessionId)
                                    updateBookmarkIcon()
                                }
                            } else {
                                setImageResource(R.drawable.vector_add_bookmark)
                                setOnClickListener {
                                    repo.bookmarkSession(sessionId)
                                    updateBookmarkIcon()
                                }
                            }

                        }
                    },
                    { session_details_bookmark.hide() }
                )
        }
    }

}
