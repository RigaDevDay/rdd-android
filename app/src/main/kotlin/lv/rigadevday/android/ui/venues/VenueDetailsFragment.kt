package lv.rigadevday.android.ui.venues

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_venue.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.other.Venue
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.openEmail
import lv.rigadevday.android.ui.openMap
import lv.rigadevday.android.ui.openWeb
import lv.rigadevday.android.utils.*

class VenueDetailsFragment : BaseFragment() {

    val EXTRA_VENUE_INDEX = "venue_index".toExtraKey()

    companion object {
        fun newInstance(index: Int) = VenueDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_VENUE_INDEX, index)
            }
        }
    }

    override val layoutId = R.layout.fragment_venue

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        val index = arguments.getInt(EXTRA_VENUE_INDEX)

        dataFetchSubscription = repo.venue(index).subscribe(
            { populateView(view, it) },
            { view.showMessage(R.string.error_message) }
        )
    }

    fun populateView(view: View, venue: Venue) = with(view) {
        venue_name.text = venue.name
        venue_address.text = venue.address
        venue_address_button.setOnClickListener { context.openMap(venue.coordinates) }
        venue_web_page_link.text = venue.web
        venue_web_page_link.setOnClickListener { context.openWeb(venue.web) }
        venue_description.text = venue.description.fromHtml()

        if (venue.email.isEmpty()) {
            venue_email_layout.hide()
        } else {
            venue_email_link.text = venue.email
            venue_email_link.setOnClickListener { context.openEmail(venue.email) }
        }
    }
}
