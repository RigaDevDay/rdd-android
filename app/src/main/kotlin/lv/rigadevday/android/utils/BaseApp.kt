package lv.rigadevday.android.utils

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import lv.rigadevday.android.utils.di.AppGraph
import lv.rigadevday.android.utils.di.AppModule
import lv.rigadevday.android.utils.di.DaggerAppGraph


class BaseApp : Application() {

    companion object {
        lateinit var graph: AppGraph
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        setupImageLoading()

        graph = DaggerAppGraph.builder()
            .appModule(AppModule(this))
            .build()
        graph.inject(this)
    }
}
