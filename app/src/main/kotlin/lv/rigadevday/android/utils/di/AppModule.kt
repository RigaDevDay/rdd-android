package lv.rigadevday.android.utils.di

import android.content.Context
import dagger.Module
import dagger.Provides
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.utils.BaseApp
import javax.inject.Singleton

@Module
class AppModule(private val application: BaseApp) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRepository(): Repository = Repository()

}
