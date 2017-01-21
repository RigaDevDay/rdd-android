package lv.rigadevday.android.ui.schedule.day.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_multiple_session.view.*
import kotlinx.android.synthetic.main.item_single_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.MultipleSessionItem
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.SingleSessionItem
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.inflate
import javax.inject.Inject

class ScheduleAdapter : RecyclerView.Adapter<ScheduleViewHolder>() {

    @Inject lateinit var repo: Repository

    var data: List<ScheduleItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        BaseApp.graph.inject(this)
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
            is SingleSessionItem -> holder.bind(item, repo)
            is MultipleSessionItem -> holder.bind(item, repo)
        }
    }

}

class ScheduleViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(item: SingleSessionItem, repo: Repository) {
        itemView.schedule_single_time.text = item.timeslot.startTime

        repo.session(item.timeslot.sessionIds.first()).subscribe { session ->
            itemView.schedule_single_title.text = session.title
        }
    }

    fun bind(item: MultipleSessionItem, repo: Repository) {
        itemView.schedule_multiple_time.text = item.timeslot.startTime
        itemView.schedule_multiple_title.text = item.timeslot.sessionIds.toString()
    }
}
