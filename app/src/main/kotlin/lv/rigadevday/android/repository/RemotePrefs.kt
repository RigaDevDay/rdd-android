package lv.rigadevday.android.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import lv.rigadevday.android.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemotePrefs @Inject constructor() {

    companion object {
        private const val ALLOW_RATING = "allow_leave_feedback"
        private const val ALLOW_DELTE_RATING = "allow_delete_feedback"

        private val DEFAULTS = mapOf(
            ALLOW_RATING to false,
            ALLOW_DELTE_RATING to false
        )
    }

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        FirebaseRemoteConfig.getInstance().apply {
            FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
                .let { setConfigSettings(it) }
            setDefaults(DEFAULTS)
        }
    }

    fun refresh() {
        remoteConfig.fetch().addOnCompleteListener { task ->
            task.takeIf { it.isSuccessful }?.run { remoteConfig.activateFetched() }
        }
    }

    val isRatingAllowed: Boolean get() = remoteConfig.getBoolean(ALLOW_RATING)

}
