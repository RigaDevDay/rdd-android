package lv.rigadevday.android.ui.schedule.sessions

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_list.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.ui.EXTRA_SESSION_DATA
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openSessionDetailsActivity
import lv.rigadevday.android.ui.schedule.TimeslotData
import lv.rigadevday.android.ui.schedule.toIntentData
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class SessionsActivity : BaseActivity() {

    @Inject lateinit var repo: Repository

    override val layoutId = R.layout.fragment_list

    private val listAdapter: SessionsAdapter = SessionsAdapter {
        openSessionDetailsActivity(it)
    }

    private var intentData: TimeslotData? = null
    private var tags: Array<String>? = null
    private var cachedSessions: List<Session>? = null

    private val filterDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@SessionsActivity)
            .setTitle(R.string.sessions_filter_title)
            .setItems(tags) { di, index ->
                if (cachedSessions != null && tags != null) {
                    val tag = tags!![index]
                    listAdapter.data = cachedSessions!!.filter { it.tags.contains(tag) }
                    supportActionBar?.title = tag
                }
            }
            .setPositiveButton(R.string.sessions_filter_clear) { di, index ->
                cachedSessions?.let { listAdapter.data = it }
                supportActionBar?.setTitle(R.string.sessions_title)
            }
            .create()
    }

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        intentData = intent.extras?.getBundle(EXTRA_SESSION_DATA)?.toIntentData()

        setupActionBar(intentData?.formattedTitle() ?: getString(R.string.sessions_title))
        homeAsUp()

        list_fragment_recycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        dataFetchSubscription = repo.sessions()
            .filter { it.speakers.isNotEmpty() }
            .filter { intentData?.ids?.contains(it.id) ?: true }
            .toList()
            .subscribe(
                { sessions ->
                    tags = sessions.flatMap { it.tags }.distinct().toTypedArray()
                    cachedSessions = sessions
                    listAdapter.data = sessions
                },
                { error -> list_fragment_recycler.showMessage(R.string.error_message) }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (intentData == null) {
            menuInflater.inflate(R.menu.menu_sessions, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> filterDialog.show()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}

private fun TimeslotData.formattedTitle() = "$readableDate - $time"
