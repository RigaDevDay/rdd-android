package lv.rigadevday.android.ui.schedule

import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.utils.BaseApp

class SessionDetailsActivity : BaseActivity() {

    override val layoutId = R.layout.activity_session_details

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {

    }

}
