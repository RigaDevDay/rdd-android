package lv.rigadevday.android.ui.schedule.sessions

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.inflate
import lv.rigadevday.android.utils.loadImage

class SessionsAdapter(val onSessionClick: (Int) -> Unit) : RecyclerView.Adapter<SessionsHolder>() {

    var data: List<Session> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionsHolder {
        return SessionsHolder(parent.inflate(R.layout.item_session))
    }

    override fun onBindViewHolder(holder: SessionsHolder, position: Int) {
        holder.bind(data[position], onSessionClick)
    }
}

class SessionsHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(session: Session, onSessionClick: (Int) -> Unit) {
        itemView.run {
            session_item_title.text = session.title
            session_item_tags.text = session.complexityAndTags

            session.speakerObjects.firstOrNull()?.let {
                session_item_strip.setBackgroundColor(session.color)

                session_item_speaker.text = it.name
                session_item_speaker.setOnClickListener { view -> openSpeaker(view, it) }

                session_item_photo.loadImage(it.photoUrl)
                session_item_photo.setOnClickListener { view -> openSpeaker(view, it) }
            }

            session_item_card.setOnClickListener { onSessionClick(session.id) }
        }
    }

    private fun openSpeaker(it: View, speaker: Speaker) {
        it.context.openSpeakerActivity(speaker.id)
    }
}
