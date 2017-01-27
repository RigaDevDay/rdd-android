package lv.rigadevday.android.ui.schedule.sessions

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.ui.EXTRA_SESSION_DATA
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.schedule.TimeslotData
import lv.rigadevday.android.ui.schedule.toIntentData
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SessionsActivity : BaseActivity(), SessionsContract {

    @Inject lateinit var repo: Repository
    @Inject lateinit var savedSessions: SessionStorage

    override val layoutId = R.layout.fragment_list

    override val contentFrameId = -1

    private var sessionsAdapter: SessionsAdapter? = null

    private var intentData: TimeslotData? = null

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        intentData = intent.extras.getBundle(EXTRA_SESSION_DATA)?.toIntentData()

        setupActionBar(intentData?.readableDate ?: getString(R.string.sessions_title))

        sessionsAdapter = SessionsAdapter(this)

        list_fragment_recycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionsAdapter
        }

        dataFetchSubscription = repo.sessions()
            .filter { it.speakers.isNotEmpty() }
            .filter { intentData?.ids?.contains(it.id) ?: true }
            .toList()
            .subscribe(
                { sessions ->
                    sessionsAdapter?.data = sessions
                },
                { error -> list_fragment_recycler.showMessage(R.string.error_message) }
            )
    }

    override fun isSelectedSession(id: Int): Boolean = intentData?.run {
        savedSessions.getSessionId(time, dateCode) != null
    } ?: false

    override fun sessionBookmarkClicked(sessionId: Int) {
    }
}

interface SessionsContract {
    fun isSelectedSession(id: Int): Boolean
    fun sessionBookmarkClicked(sessionId: Int)
}
