package lv.rigadevday.android.utils.di

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.SessionStorage
import lv.rigadevday.android.utils.BaseApp
import lv.rigadevday.android.utils.analytics.Analytics
import lv.rigadevday.android.utils.auth.AuthStorage
import javax.inject.Singleton

@Module
class AppModule(private val application: BaseApp) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRepository(authStorage: AuthStorage): Repository = Repository(authStorage)

    @Provides
    @Singleton
    fun provideAnalytics() = Analytics(application)

    @Provides
    @Singleton
    fun provideSessionStorage(analytics: Analytics): SessionStorage = SessionStorage(application, analytics)

    @Provides
    @Singleton
    fun provideNotificationManager(): NotificationManager
        = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

}
