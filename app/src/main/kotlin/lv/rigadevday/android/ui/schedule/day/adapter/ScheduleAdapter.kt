package lv.rigadevday.android.ui.schedule.day.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_multiple_session.view.*
import kotlinx.android.synthetic.main.item_non_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.repository.model.schedule.Session.Companion.TBD
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.*
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.inflate
import lv.rigadevday.android.utils.loadImage
import javax.inject.Inject

class ScheduleAdapter(val dateCode: String) : RecyclerView.Adapter<ScheduleViewHolder>() {

    @Inject lateinit var storage: SessionStorage

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
        is NonSessionTimeslot -> R.layout.item_non_session
        is MultiSessionTimeslot -> R.layout.item_multiple_session
        is SingleSessionTimeslot -> R.layout.item_multiple_session
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = ScheduleViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = data[position]
        when (item) {
            is NonSessionTimeslot -> holder.bind(item)
            is MultiSessionTimeslot -> holder.bind(item, storage.getSessionId(item.timeslot.startTime, dateCode))
            is SingleSessionTimeslot -> holder.bind(item)
        }
    }

}

class ScheduleViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(item: NonSessionTimeslot) {
        itemView.schedule_single_time.text = item.timeslot.formattedStartTime
        itemView.schedule_single_title.text = item.timeslot.sessionObjects.first().title
    }

    fun bind(item: MultiSessionTimeslot, savedSessionId: Int?) {
        itemView.schedule_multiple_time.text = item.timeslot.formattedStartTime

        val session = item.timeslot.sessionObjects.firstOrNull { it.speakers.isNotEmpty() } ?: TBD

        itemView.schedule_multiple_title.text = session.title
        itemView.schedule_multiple_room.text = session.room

        val speaker = session.speakerObjects.first()
        itemView.schedule_multiple_speaker.text = speaker.name
        itemView.schedule_multiple_speaker_photo.loadImage(speaker.photoUrl)
    }

    fun bind(item: SingleSessionTimeslot) {
        itemView.schedule_multiple_time.text = item.timeslot.startTime
        itemView.schedule_multiple_title.text = item.timeslot.sessionIds.toString()
    }
}
