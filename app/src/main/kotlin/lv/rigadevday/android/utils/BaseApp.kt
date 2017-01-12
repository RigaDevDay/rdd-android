package lv.rigadevday.android.utils

import android.app.Application
import lv.rigadevday.android.utils.di.AppGraph
import lv.rigadevday.android.utils.di.AppModule
import lv.rigadevday.android.utils.di.DaggerAppGraph


class BaseApp : Application() {

    companion object {
        lateinit var graph: AppGraph
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerAppGraph.builder()
            .appModule(AppModule(this))
            .build()
        graph.inject(this)
    }
}
