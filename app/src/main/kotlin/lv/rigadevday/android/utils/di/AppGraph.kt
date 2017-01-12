package lv.rigadevday.android.utils.di

import dagger.Component
import lv.rigadevday.android.utils.BaseApp
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppGraph {

    // App
    fun inject(app: BaseApp)

    // Fragments
    // fragment injects go here

}
