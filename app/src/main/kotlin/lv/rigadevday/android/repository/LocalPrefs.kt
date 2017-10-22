package lv.rigadevday.android.repository

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPrefs @Inject constructor(
    private val context: Context
) {
    companion object {
        private val LAST_TAG = "last_used_tag"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("local_prefs", Context.MODE_PRIVATE)
    }

    var lastUsedTag: String
        get() = prefs.getString(LAST_TAG, "")
        set(newVal) {
            prefs.edit().putString(LAST_TAG, newVal).apply()
        }

}
