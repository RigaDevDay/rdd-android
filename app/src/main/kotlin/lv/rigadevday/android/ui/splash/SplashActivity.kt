package lv.rigadevday.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.tabs.TabActivity
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.showMessage
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    val TIME_TO_EXIT_APP: Long = 5000
    val TIME_OUT_SECONDS: Long = 5

    @Inject lateinit var repo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.graph.inject(this)

        // Small warmup for Firebase and Rx caches
        Completable.concat(listOf(
                repo.speakers().toList().toCompletable(),
                repo.sessions().toList().toCompletable(),
                repo.schedule().toList().toCompletable()
        ))
                // If there is no internet or persistent data, call timeout
                .timeout(TIME_OUT_SECONDS, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            val intent = Intent(this, TabActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        {
                            showMessage(R.string.error_connection_message)
                            exitApp()
                        }
                )
    }

    fun exitApp() {
        val handler = Handler()
        val r = Runnable { finish() }
        handler.postDelayed(r, TIME_TO_EXIT_APP)
    }
}
