package lv.rigadevday.android.ui.schedule

import kotlinx.android.synthetic.main.activity_session_details.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.EXTRA_SESSION_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.fromHtml
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SessionDetailsActivity : BaseActivity() {

    @Inject lateinit var repo: Repository

    override val layoutId = R.layout.activity_session_details

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        val sessionId = intent.extras.getInt(EXTRA_SESSION_ID)

        session_close.setOnClickListener { finish() }


        dataFetchSubscription = repo.session(sessionId)
            .subscribe(
                { session ->
                    session_details_title.text = session.title
                    session_details_speaker.text = session.speakerObjects.first().name
                    session_details_tags.text = session.complexityAndTags
                    session_details_description.text = session.description.fromHtml()

                    session_details_bookmark.setOnClickListener { saveBookmark(sessionId) }
                },
                { session_details_description.showMessage(R.string.error_message) }

            )
    }

    private fun saveBookmark(sessionId: Int) {
        // TODO save session

        session_details_description.showMessage(R.string.session_bookmark_saved)
    }

}
