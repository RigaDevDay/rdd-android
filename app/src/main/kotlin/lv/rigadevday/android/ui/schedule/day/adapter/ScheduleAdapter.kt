package lv.rigadevday.android.ui.schedule.day.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_multiple_session.view.*
import kotlinx.android.synthetic.main.item_non_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.schedule.Session
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
            is SingleSessionTimeslot -> holder.bind(item, contract)
        }
    }
}

class ScheduleViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(item: NonSessionTimeslot) = with(itemView) {
        schedule_single_time.text = item.timeslot.formattedStartTime
        schedule_single_title.text = item.timeslot.sessionObjects.first().title
    }


    fun bind(item: SingleSessionTimeslot, contract: DayScheduleContract) = with(itemView) {
        val session = item.timeslot.sessionObjects.firstOrNull { it.speakers.isNotEmpty() } ?: TBD
        populateSessionContent(session, contract)
    }


    fun bind(item: MultiSessionTimeslot, contract: DayScheduleContract) = with(itemView) {
        itemView.schedule_multiple_time.text = item.timeslot.formattedStartTime

        val savedSessionId = contract.getSavedSessionId(item.timeslot)

        if (savedSessionId != null) {
            val session = item.timeslot.sessionObjects.firstOrNull { savedSessionId == it.id } ?: TBD
            populateSessionContent(session, contract)

            schedule_multiple_card.setOnLongClickListener {
                contract.openTimeslot(item.timeslot)
                true
            }
            schedule_multiple_card.setOnClickListener {
                contract.openSession(savedSessionId)
            }
        } else {
            schedule_multiple_placeholder.show()
            schedule_multiple_content.hide()

            schedule_multiple_card.setOnLongClickListener(null)
            schedule_multiple_card.setOnClickListener {
                contract.openTimeslot(item.timeslot)
            }
        }

    }

    private fun View.populateSessionContent(session: Session, contract: DayScheduleContract) {
        schedule_multiple_placeholder.hide()
        schedule_multiple_content.show()

        schedule_multiple_title.text = session.title
        schedule_multiple_room.text = session.room

        session.speakerObjects.firstOrNull()?.let { speaker ->
            schedule_multiple_strip.setBackgroundColor(session.color)

            schedule_multiple_speaker.text = speaker.name
            schedule_multiple_speaker.setOnClickListener { contract.openSpeaker(speaker.id) }

            schedule_multiple_speaker_photo.loadCircleAvatar(speaker.photoUrl)
            schedule_multiple_speaker_photo.setOnClickListener { contract.openSpeaker(speaker.id) }
        }
    }

}
