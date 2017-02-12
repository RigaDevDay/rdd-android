package lv.rigadevday.android.ui.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.part_toolbar.*
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.tabs.TabActivity
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject lateinit var repo : Repository

    abstract val layoutId: Int

    abstract fun inject()

    abstract fun viewReady(view: View)

    protected var dataFetchSubscription: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
        = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        viewReady(view)
    }

    override fun onStop() {
        dataFetchSubscription?.dispose()
        super.onStop()
    }

    fun setupActionBar(@StringRes title: Int) {
        setupActionBar(getString(title))
    }

    fun setupActionBar(title: String) {
        (activity as TabActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.title = title
        }
    }
}
