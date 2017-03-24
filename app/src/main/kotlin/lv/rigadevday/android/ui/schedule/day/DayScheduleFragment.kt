package lv.rigadevday.android.ui.schedule.day

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_day_schedule.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.repository.model.schedule.Timeslot
import lv.rigadevday.android.ui.EXTRA_SESSION_DATA
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.openSessionDetailsActivity
import lv.rigadevday.android.ui.openSessionsActivity
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.ui.schedule.TimeslotData
import lv.rigadevday.android.ui.schedule.day.adapter.MultiSessionTimeslot
import lv.rigadevday.android.ui.schedule.day.adapter.NonSessionTimeslot
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleAdapter
import lv.rigadevday.android.ui.schedule.day.adapter.SingleSessionTimeslot
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

    private val listAdapter = ScheduleAdapter(this)

    private lateinit var timeslotData: TimeslotData

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        timeslotData = arguments.getBundle(EXTRA_SESSION_DATA).toIntentData()

        with(view.schedule_recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        dataFetchSubscription = repo.scheduleDayTimeslots(timeslotData.dateCode)
            .map {
                when {
                    it.sessionObjects.size > 1 -> MultiSessionTimeslot(it)
                    it.sessionObjects.first().speakers.isNotEmpty() -> SingleSessionTimeslot(it)
                    else -> NonSessionTimeslot(it)
                }
            }
            .toList()
            .subscribe(
                { listAdapter.data = it },
                { view.showMessage(R.string.error_message) }
            )
    }

    override fun getSavedSessionId(timeslot: Timeslot) = storage.getSessionId(timeslot.startTime, timeslotData.dateCode)

    override fun openTimeslot(timeslot: Timeslot) {
        context.openSessionsActivity(
            timeslotData.copy(time = timeslot.startTime, ids = timeslot.sessionIds)
        )
    }

    override fun openSession(sessionId: Int) {
        context.openSessionDetailsActivity(sessionId)
    }

    override fun openSpeaker(speakerId: Int) {
        context.openSpeakerActivity(speakerId)
    }

    override fun onResume() {
        super.onResume()
        listAdapter.notifyDataSetChanged()
    }

}

interface DayScheduleContract {
    fun getSavedSessionId(timeslot: Timeslot): Int?
    fun openTimeslot(timeslot: Timeslot)
    fun openSession(sessionId: Int)
    fun openSpeaker(speakerId: Int)
}
