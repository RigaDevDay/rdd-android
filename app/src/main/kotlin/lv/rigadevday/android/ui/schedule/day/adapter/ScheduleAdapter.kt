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
import lv.rigadevday.android.ui.openSessionsActivity
import lv.rigadevday.android.ui.schedule.day.adapter.ScheduleItem.*
import lv.rigadevday.android.utils.*
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

    fun bind(item: MultiSessionTimeslot, savedSessionId: Int?) {
        with(itemView) {
            itemView.schedule_multiple_time.text = item.timeslot.formattedStartTime

            if (savedSessionId != null) {
                schedule_multiple_placeholder.hide()
                schedule_multiple_content.unhide()
                showSession(item)
            } else {
                schedule_multiple_placeholder.unhide()
                schedule_multiple_content.hide()
                showPlaceholder(item.timeslot.sessionIds)
            }

        }
    }

    private fun View.showSession(item: MultiSessionTimeslot) {

        val session = item.timeslot.sessionObjects.firstOrNull { it.speakers.isNotEmpty() } ?: TBD

        itemView.schedule_multiple_title.text = session.title
        itemView.schedule_multiple_room.text = session.room

        session.speakerObjects.firstOrNull()?.let { speaker ->
            itemView.schedule_multiple_speaker.text = speaker.name
            itemView.schedule_multiple_speaker_photo.loadImage(speaker.photoUrl)
        }

        schedule_multiple_card.setOnLongClickListener {
            openSessionChooser(item.timeslot.sessionIds)
            return@setOnLongClickListener true
        }
        schedule_multiple_card.setOnClickListener {
            openSessionChooser(item.timeslot.sessionIds)
        }
    }

    private fun View.showPlaceholder(sessionIds: List<Int>) {
        schedule_multiple_card.setOnLongClickListener(null)
        schedule_multiple_card.setOnClickListener {
            openSessionChooser(sessionIds)
        }
    }

    private fun openSessionChooser(sessionIds: List<Int>) {
        itemView.context.openSessionsActivity()
    }

}
