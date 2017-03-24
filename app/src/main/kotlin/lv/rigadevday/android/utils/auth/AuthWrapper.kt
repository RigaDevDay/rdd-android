package lv.rigadevday.android.utils.auth

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import lv.rigadevday.android.R
import lv.rigadevday.android.ui.base.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthWrapper @Inject constructor(
    private val authStorage: AuthStorage
) : GoogleApiClient.OnConnectionFailedListener {

    companion object {
        const val RC_SIGN_IN: Int = 213
    }

    private lateinit var googleClient: GoogleApiClient
    var contract: LoginContract? = null

    fun bind(activity: BaseActivity, loginContract: LoginContract) {
        contract = loginContract

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleApiClient.Builder(activity)
            .enableAutoManage(activity, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        contract?.loginFailed()
    }

    fun logIn(activity: BaseActivity) {
        googleClient.let {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(it)
            activity.startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    fun handleLoginResponse(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val acct = result.signInAccount
                contract?.firebaseAuthWithGoogle(acct!!)
            } else {
                contract?.loginFailed()
            }
        }
    }

    fun logOut() {
        if (googleClient.isConnected) {
            logoutFromClient()
        } else {
            googleClient.registerConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                override fun onConnected(p0: Bundle?) {
                    logoutFromClient()
                }

                override fun onConnectionSuspended(p0: Int) {
                }
            })
            googleClient.connect()
        }
    }

    private fun logoutFromClient() {
        Auth.GoogleSignInApi.signOut(googleClient).setResultCallback {
            authStorage.signOut()
            contract?.logoutSuccess()
        }
    }
}
