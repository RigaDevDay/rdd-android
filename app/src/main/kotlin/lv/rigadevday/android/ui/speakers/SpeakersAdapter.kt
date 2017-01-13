package lv.rigadevday.android.ui.speakers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_speaker.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.utils.hide
import lv.rigadevday.android.utils.inflate
import lv.rigadevday.android.utils.loadImage
import lv.rigadevday.android.utils.unhide

class SpeakersAdapter : RecyclerView.Adapter<SpeakerViewHolder>() {

    var data: List<Speaker> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = SpeakerViewHolder(parent.inflate(R.layout.item_speaker))

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        holder.bind(data[position], onItemClick)
    }

    override fun getItemCount(): Int = data.size

}

class SpeakerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(speaker: Speaker, onClick: ((Int) -> Unit)?) {
        itemView.setOnClickListener { onClick?.invoke(speaker.id) }

        itemView.speakers_item_name.text = speaker.name
        itemView.speakers_item_company.text = speaker.company

        itemView.speakers_item_image.loadImage(speaker.photoUrl, R.drawable.vector_speaker_placeholder)

        itemView.speakers_item_bagde.apply {
            if (speaker.badges.isEmpty()) hide()
            else {
                unhide()
                text = speaker.badges[0].name
            }
        }
    }

}
