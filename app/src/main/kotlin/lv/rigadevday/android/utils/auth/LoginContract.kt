package lv.rigadevday.android.utils.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface LoginContract {
    fun loginSuccess()
    fun loginFailed()
    fun logoutSuccess()
    fun firebaseAuthWithGoogle(acc: GoogleSignInAccount)
}
