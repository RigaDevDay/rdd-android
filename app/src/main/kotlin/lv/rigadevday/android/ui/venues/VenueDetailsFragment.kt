package lv.rigadevday.android.ui.venues

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.fragment_venue.view.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.other.Venue
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.openMap
import lv.rigadevday.android.ui.openWeb
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.fromHtml
import lv.rigadevday.android.utils.loadVenueImage
import lv.rigadevday.android.utils.showMessage
import lv.rigadevday.android.utils.toExtraKey

class VenueDetailsFragment : BaseFragment() {

    companion object {
        val EXTRA_VENUE_INDEX = "venue_index".toExtraKey()

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

    private fun populateView(view: View, venue: Venue) = with(view) {
        venue_image.loadVenueImage(venue.imageUrl)

        venue_name.text = venue.name
        venue_address.text = venue.address
        venue_address_button.setOnClickListener { context.openMap(venue.coordinates) }
        venue_web_page_link.text = venue.web
        venue_web_page_link.setOnClickListener { context.openWeb(venue.web) }

        venue_description.movementMethod = LinkMovementMethod.getInstance()
        venue_description.text = venue.description.fromHtml()
    }
}
