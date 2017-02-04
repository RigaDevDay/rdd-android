package lv.rigadevday.android.ui.orginizers

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_list.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.orginizers.adapter.PartnersAdapter
import lv.rigadevday.android.ui.orginizers.adapter.PartnersItem
import lv.rigadevday.android.ui.orginizers.adapter.PartnersItem.PartnerLogo
import lv.rigadevday.android.ui.orginizers.adapter.PartnersItem.PartnerTitle
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage

class PartnersFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_list

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    private lateinit var listAdapter: PartnersAdapter

    override fun viewReady(view: View) {
        setupActionBar(R.string.partners_title)

        dataFetchSubscription = repo.partners()
            .flatMap {
                Observable.just<PartnersItem>(PartnerTitle(it.title))
                    .concatWith(Observable.fromIterable(it.logos.map(::PartnerLogo)))
            }
            .toList()
            .subscribe(
                { partner ->
                    listAdapter = PartnersAdapter(partner)

                    with(list_fragment_recycler) {
                        layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false).apply {
                            spanSizeLookup = spanLookup
                        }
                        adapter = listAdapter
                    }
                },
                { view.showMessage(R.string.error_message) }
            )
    }

    val spanLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int) = when (listAdapter.getItemViewType(position)) {
            R.layout.item_organizers_logo -> 1
            else -> 3
        }
    }

}

