package lv.rigadevday.android.ui.schedule.day.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_multiple_session.view.*
import kotlinx.android.synthetic.main.item_single_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.MultipleSessionItem
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.SingleSessionItem
import lv.rigadevday.android.utils.inflate

class ScheduleAdapter : RecyclerView.Adapter<ScheduleViewHolder>() {

    var data: List<ScheduleItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = when (data[position]) {
        is SingleSessionItem -> R.layout.item_single_session
        is MultipleSessionItem -> R.layout.item_multiple_session
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = ScheduleViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = data[position]
        when (item) {
            is SingleSessionItem -> holder.bind(item)
            is MultipleSessionItem -> holder.bind(item)
        }
    }

}


class ScheduleViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(scheduleItem: SingleSessionItem) {
        itemView.schedule_single_time.text = scheduleItem.timeslot.startTime
        itemView.schedule_single_title.text = scheduleItem.timeslot.sessionIds.toString()
    }

    fun bind(scheduleItem: MultipleSessionItem) {
        itemView.schedule_multiple_time.text = scheduleItem.timeslot.startTime
        itemView.schedule_multiple_title.text = scheduleItem.timeslot.sessionIds.toString()
    }
}
