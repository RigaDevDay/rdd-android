package lv.rigadevday.android.ui.speakers

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.part_toolbar.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.TabActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.logE
import javax.inject.Inject

class SpeakerListFragment : Fragment() {

    @Inject lateinit var repo: Repository

    private var adapter: SpeakersAdapter = SpeakersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
        = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApp.graph.inject(this)

        setupActionBar(R.string.tab_speakers)

        list_fragment_recycler.layoutManager = GridLayoutManager(view.context, 2)
        list_fragment_recycler.adapter = adapter

        adapter.onItemClick = { id ->
            id.logE()
        }

        repo.speakers().subscribe { list -> adapter.data = list }
    }

    private fun setupActionBar(@StringRes title: Int) {
        (activity as TabActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.title = getString(title)
        }
    }

}
