package lv.rigadevday.android.ui.speakers

import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_list.*
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseFragment
import lv.rigadevday.android.ui.openSpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage

class SpeakerListFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_list

    private var adapter: SpeakersAdapter = SpeakersAdapter()

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady(view: View) {
        setupActionBar(R.string.tab_speakers)

        list_fragment_recycler.layoutManager = GridLayoutManager(view.context, 2)
        list_fragment_recycler.adapter = adapter

        adapter.onItemClick = {
            context.openSpeakerActivity(it)
        }

        repo.speakers().toList().subscribe(
            { list -> adapter.data = list },
            { view.showMessage(R.string.error_message) }
        )
    }

}
