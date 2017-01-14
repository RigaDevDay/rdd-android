package lv.rigadevday.android.utils.di

import dagger.Component
import lv.rigadevday.android.ui.speakers.SpeakerListFragment
import lv.rigadevday.android.ui.speakers.speaker.SpeakerActivity
import lv.rigadevday.android.utils.BaseApp
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppGraph {

    // App
    fun inject(app: BaseApp)

    // Activity
    fun inject(activity: SpeakerActivity)

    // Fragments
    fun inject(fragment: SpeakerListFragment)

}
