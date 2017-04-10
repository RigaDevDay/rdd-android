package lv.rigadevday.android.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.part_toolbar.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.utils.auth.AuthWrapper
import lv.rigadevday.android.utils.auth.LoginContract
import lv.rigadevday.android.utils.showMessage
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), LoginContract {

    @Inject lateinit var loginWrapper: AuthWrapper
    @Inject lateinit var repo: Repository

    abstract val layoutId: Int
    open val contentFrameId: Int = -1
    open val ignoreUiUpdates: Boolean = false

    abstract fun inject()

    abstract fun viewReady()

    protected var dataFetchSubscription: Disposable? = null
    protected var uiUpdateSubscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        setContentView(layoutId)

        viewReady()
        loginWrapper.bind(this, this)
    }

    override fun onResume() {
        super.onResume()
        loginWrapper.contract = this
        refreshLoginState()

        if (!ignoreUiUpdates) {
            uiUpdateSubscription = repo.cacheUpdated
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewReady() }
        }
    }

    override fun onPause() {
        uiUpdateSubscription?.takeUnless { it.isDisposed }?.dispose()
        super.onPause()
    }

    override fun onStop() {
        dataFetchSubscription?.dispose()
        super.onStop()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginWrapper.handleLoginResponse(requestCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }

    protected fun setupActionBar(@StringRes title: Int) {
        setupActionBar(getString(title))
    }

    protected fun setupActionBar(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
    }

    protected fun homeAsUp() {
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun Fragment.setAsMain() {
        supportFragmentManager.beginTransaction()
            .replace(contentFrameId, this, this.tag)
            .commit()
    }

    open fun refreshLoginState() {}

    override fun loginSuccess() {
        showMessage(R.string.auth_login_success)
        refreshLoginState()
    }

    @CallSuper
    override fun loginFailed() {
        showMessage(R.string.auth_login_failed)
        refreshLoginState()
    }

    @CallSuper
    override fun logoutSuccess() {
        showMessage(R.string.auth_logout_happened)
        refreshLoginState()
    }

    override fun firebaseAuthWithGoogle(acc: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)

        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    loginFailed()
                } else {
                    loginSuccess()
                }
            }
    }

}
