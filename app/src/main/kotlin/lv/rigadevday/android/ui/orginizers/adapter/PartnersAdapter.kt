package lv.rigadevday.android.ui.orginizers.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_organizers_logo.view.*
import kotlinx.android.synthetic.main.item_organizers_title.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.partners.Logo
import lv.rigadevday.android.ui.openWeb
import lv.rigadevday.android.ui.orginizers.adapter.PartnersItem.PartnerLogo
import lv.rigadevday.android.ui.orginizers.adapter.PartnersItem.PartnerTitle
import lv.rigadevday.android.utils.loadLogo

class PartnersAdapter(val data: List<PartnersItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = when (data[position]) {
        is PartnerLogo -> R.layout.item_organizers_logo
        is PartnerTitle -> R.layout.item_organizers_title
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_organizers_logo -> LogoHolder(view)
            else -> TitleHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (item) {
            is PartnerLogo -> (holder as LogoHolder).bind(item.partner)
            is PartnerTitle -> (holder as TitleHolder).bind(item.title)
        }
    }


    private class LogoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(logo: Logo) = with(itemView) {
            organizers_logo_item_image.loadLogo(logo.logoUrl)
            setOnClickListener { context.openWeb(logo.url) }
        }
    }

    private class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String) = with(itemView) {
            organizers_logo_item_title.text = title

        }
    }

}
