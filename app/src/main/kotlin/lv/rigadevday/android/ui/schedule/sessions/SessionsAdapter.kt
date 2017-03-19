package lv.rigadevday.android.ui.schedule.sessions

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_session.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.utils.inflate
import lv.rigadevday.android.utils.loadCircleAvatar

class SessionsAdapter(val contract: SessionContract) : RecyclerView.Adapter<SessionsHolder>() {

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
        holder.bind(data[position], contract)
    }
}

class SessionsHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(session: Session, contract: SessionContract) {
        itemView.run {
            val bgColor =
                if (contract.isSessionBookmarked(session.id)) R.color.session_bookmarked
                else R.color.screen_background
            session_item_card_content.setBackgroundColor(ContextCompat.getColor(itemView.context, bgColor))

            session_item_title.text = session.title
            session_item_tags.text = session.complexityAndTags

            session.speakerObjects.firstOrNull()?.let { (id, _, name, _, _, photoUrl) ->
                session_item_strip.setBackgroundColor(session.color)

                session_item_speaker.text = name
                session_item_speaker.setOnClickListener { contract.openSpeaker(id) }

                session_item_photo.loadCircleAvatar(photoUrl)
                session_item_photo.setOnClickListener { contract.openSpeaker(id) }
            }

            session_item_card.setOnClickListener { contract.openSession(session.id) }
        }
    }

}
