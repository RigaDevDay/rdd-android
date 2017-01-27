package lv.rigadevday.android.ui.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.part_toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val contentFrameId: Int

    abstract fun inject()

    abstract fun viewReady()

    protected var dataFetchSubscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        setContentView(layoutId)

        viewReady()
    }

    override fun onStop() {
        dataFetchSubscription?.dispose()
        super.onStop()
    }

    protected fun setupActionBar(@StringRes title: Int) {
        setupActionBar(getString(title))
    }

    protected fun setupActionBar(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
    }

    fun setFragment(nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(contentFrameId, nextFragment, nextFragment.tag)
            .commit()
    }
}
