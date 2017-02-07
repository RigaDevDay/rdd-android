package lv.rigadevday.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Completable
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.ui.tabs.TabActivity
import lv.rigadevday.android.utils.BaseApp
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject lateinit var repo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.graph.inject(this)

        // Small warmup for Firebase and Rx caches
        Completable.concat(listOf(
            repo.speakers().toList().toCompletable(),
            repo.sessions().toList().toCompletable(),
            repo.schedule().toList().toCompletable()
        )).subscribe {
            val intent = Intent(this, TabActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
