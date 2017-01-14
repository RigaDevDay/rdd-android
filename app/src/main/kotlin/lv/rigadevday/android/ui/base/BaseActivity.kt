package lv.rigadevday.android.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val contentFrameId: Int

    abstract fun viewReady()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        viewReady()
    }

    fun setFragment(nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(contentFrameId, nextFragment, nextFragment.tag)
            .commit()
    }
}
