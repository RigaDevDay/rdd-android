package lv.rigadevday.android.ui.schedule.sessions

import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.utils.BaseApp
import javax.inject.Inject

class SessionsActivity : BaseActivity() {

    @Inject lateinit var repo: Repository
    @Inject lateinit var savedSessions: SessionStorage

    override val layoutId = R.layout.fragment_list

    override val contentFrameId = -1

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        setupActionBar(R.string.sessions_title)
    }

    data class SessionIntentData(
        val date: String,
        val dataCode: String,
        val time: String,
        val ids: List<Int>
    )

}
