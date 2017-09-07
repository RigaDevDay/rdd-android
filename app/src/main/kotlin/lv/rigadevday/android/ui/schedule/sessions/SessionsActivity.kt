package lv.rigadevday.android.ui.schedule.sessions

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_list.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openSessionDetailsActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage

class SessionsActivity : BaseActivity() {

    override val layoutId = R.layout.fragment_list

    private val listAdapter: SessionsAdapter = SessionsAdapter(object : SessionContract {
        override fun openSession(id: Int) {
            openSessionDetailsActivity(id)
        }

        override fun openSpeaker(id: Int) {
            openSpeakerActivity(id)
        }

    })

    private var tags: Array<String>? = null
    private var cachedSessions: List<Session>? = null

    private val filterDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@SessionsActivity)
            .setTitle(R.string.sessions_filter_title)
            .setItems(tags) { _, index ->
                if (cachedSessions != null && tags != null) {
                    val tag = tags!![index]
                    listAdapter.data = cachedSessions!!.filter { it.tags.contains(tag) }
                    supportActionBar?.title = tag
                }
            }
            .setPositiveButton(R.string.sessions_filter_clear) { _, _ ->
                cachedSessions?.let { listAdapter.data = it }
                supportActionBar?.setTitle(R.string.sessions_title)
            }
            .create()
    }

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        setupActionBar(getString(R.string.sessions_title))
        homeAsUp()

        list_fragment_recycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        dataFetchSubscription = repo.sessions()
            .filter { it.speakers.isNotEmpty() }
            .toList()
            .subscribe(
                { sessions ->
                    tags = sessions.flatMap { it.tags }.distinct().toTypedArray()
                    cachedSessions = sessions
                    listAdapter.data = sessions
                },
                { _ -> list_fragment_recycler.showMessage(R.string.error_message) }
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_FIRST_USER) finish()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        listAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sessions, menu)
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

interface SessionContract {
    fun openSession(id: Int)
    fun openSpeaker(id: Int)
}
