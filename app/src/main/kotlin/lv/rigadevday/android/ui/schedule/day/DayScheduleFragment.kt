package lv.rigadevday.android.ui.schedule.day

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_day_schedule.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.repository.model.schedule.Timeslot
import lv.rigadevday.android.ui.EXTRA_SESSION_DATA
import lv.rigadevday.android.ui.REQUEST_CODE_SESSIONS
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.openSessionsActivity
import lv.rigadevday.android.ui.schedule.TimeslotData
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleAdapter
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem
import lv.rigadevday.android.ui.schedule.toBundle
import lv.rigadevday.android.ui.schedule.toIntentData
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

class DayScheduleFragment : BaseFragment(), DayScheduleContract {

    companion object {
        fun newInstance(dateCode: String, readableDate: String) = DayScheduleFragment().apply {
            arguments = Bundle().apply {
                putBundle(EXTRA_SESSION_DATA, TimeslotData(readableDate, dateCode).toBundle())
            }
        }
    }

    @Inject lateinit var storage: SessionStorage

    override val layoutId = R.layout.fragment_day_schedule

    private lateinit var timeslotData: TimeslotData
    private lateinit var adapter: ScheduleAdapter

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        timeslotData = arguments.getBundle(EXTRA_SESSION_DATA).toIntentData()
        adapter = ScheduleAdapter(this)

        view.schedule_recycler.layoutManager = LinearLayoutManager(context)
        view.schedule_recycler.adapter = adapter

        dataFetchSubscription = repo.scheduleDayTimeslots(timeslotData.dateCode)
            .map {
                when {
                    it.sessionObjects.size > 1 -> ScheduleItem.MultiSessionTimeslot(it)
                    it.sessionObjects.first().speakers.isNotEmpty() -> ScheduleItem.SingleSessionTimeslot(it)
                    else -> ScheduleItem.NonSessionTimeslot(it)
                }
            }
            .toList()
            .subscribe(
                { adapter.data = it },
                { view.showMessage(R.string.error_message) }
            )
    }

    override fun getSavedSessionId(timeslot: Timeslot): Int? {
        return storage.getSessionId(timeslot.startTime, timeslotData.dateCode)
    }

    override fun timeslotClicked(timeslot: Timeslot) {
        openSessionsActivity(
            timeslotData.copy(time = timeslot.startTime, ids = timeslot.sessionIds)
        )
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SESSIONS) {
            adapter.notifyDataSetChanged()
        }
    }
}

interface DayScheduleContract {
    fun getSavedSessionId(timeslot: Timeslot): Int?
    fun timeslotClicked(timeslot: Timeslot)
}
