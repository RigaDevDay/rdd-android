package lv.rigadevday.android.ui.schedule.day

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_day_schedule.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleAdapter
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage
import lv.rigadevday.android.utils.toExtraKey

class DayScheduleFragment : BaseFragment() {

    companion object {
        val EXTRA_DATE_CODE = "date_code".toExtraKey()

        fun newInstance(date: String) = DayScheduleFragment().apply {
            arguments = Bundle().apply { putString(EXTRA_DATE_CODE, date) }
        }
    }

    override val layoutId = R.layout.fragment_day_schedule

    private lateinit var dateCode: String
    private var adapter: ScheduleAdapter? = null

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        dateCode = arguments.getString(EXTRA_DATE_CODE)
        adapter = ScheduleAdapter()
        view.schedule_recycler.layoutManager = LinearLayoutManager(context)
        view.schedule_recycler.adapter = adapter

        dataFetchSubscription = repo.scheduleDayTimeslots(dateCode)
            .map {
                when {
                    it.sessionObjects.size > 1 -> ScheduleItem.MultiSessionTimeslot(it)
                    it.sessionObjects.first().speakers.isNotEmpty() -> ScheduleItem.SingleSessionTimeslot(it)
                    else -> ScheduleItem.NonSessionTimeslot(it)
                }
            }
            .toList()
            .subscribe(
                { adapter?.data = it },
                { view.showMessage(R.string.error_message) }
            )
    }

}
