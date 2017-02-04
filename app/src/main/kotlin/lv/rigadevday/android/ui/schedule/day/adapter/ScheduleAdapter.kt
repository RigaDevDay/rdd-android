package lv.rigadevday.android.ui.schedule.day.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_multiple_session.view.*
import kotlinx.android.synthetic.main.item_non_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.schedule.Session.Companion.TBD
import lv.rigadevday.android.ui.schedule.day.DayScheduleContract
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.*
import lv.rigadevday.android.utils.*

class ScheduleAdapter(val contract: DayScheduleContract) : RecyclerView.Adapter<ScheduleViewHolder>() {

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
            is MultiSessionTimeslot -> holder.bind(item, contract)
            is SingleSessionTimeslot -> holder.bind(item)
        }
    }
}

class ScheduleViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(item: NonSessionTimeslot) {
        with(itemView) {
            schedule_single_time.text = item.timeslot.formattedStartTime
            schedule_single_title.text = item.timeslot.sessionObjects.first().title
        }
    }

    fun bind(item: SingleSessionTimeslot) {
        val session = item.timeslot.sessionObjects.firstOrNull { it.speakers.isNotEmpty() } ?: TBD

        with(itemView) {
            schedule_multiple_title.text = session.title
            schedule_multiple_room.text = session.room

            session.speakerObjects.firstOrNull()?.let { speaker ->
                schedule_multiple_speaker.text = speaker.name
                schedule_multiple_speaker_photo.loadImage(speaker.photoUrl)
            }
        }
    }

    fun bind(item: MultiSessionTimeslot, contract: DayScheduleContract) {
        with(itemView) {
            itemView.schedule_multiple_time.text = item.timeslot.formattedStartTime

            val savedSessionId = contract.getSavedSessionId(item.timeslot)

            if (savedSessionId != null) {
                schedule_multiple_placeholder.hide()
                schedule_multiple_content.unhide()
                showSession(item, savedSessionId)
            } else {
                schedule_multiple_placeholder.unhide()
                schedule_multiple_content.hide()
            }

            schedule_multiple_card.setOnClickListener {
                contract.timeslotClicked(item.timeslot)
            }
        }
    }

    private fun showSession(item: MultiSessionTimeslot, sessionId: Int) {
        val session = item.timeslot.sessionObjects.firstOrNull { it.id == sessionId } ?: TBD

        itemView.schedule_multiple_title.text = session.title
        itemView.schedule_multiple_room.text = session.room

        session.speakerObjects.firstOrNull()?.let { speaker ->
            itemView.schedule_multiple_speaker.text = speaker.name
            itemView.schedule_multiple_speaker_photo.loadImage(speaker.photoUrl)
        }
    }

}
