package lv.rigadevday.android.repository

import android.content.Context
import android.content.SharedPreferences
import lv.rigadevday.android.utils.analytics.Analytics
import javax.inject.Inject

class SessionStorage @Inject constructor(
    context: Context,
    val analytics: Analytics
) {

    companion object {
        private val PREF_NAME = "saved_sessions"
        private val KEY = "%s_%s"
    }

    private val preference: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(time: String, date: String, sessionId: Int) {
        logIfSessionExisted(date, time)

        preference.edit().putInt(KEY.format(time, date), sessionId).apply()
        analytics.logSessionBookmarked(time, date, sessionId)
    }

    fun getSessionId(time: String, date: String): Int? {
        val key = KEY.format(time, date)
        val id = preference.getInt(key, -1)
        return if (id >= 0) id else null
    }

    fun removeSession(time: String, date: String) {
        logIfSessionExisted(date, time)

        preference.edit().remove(KEY.format(time, date)).apply()
    }

    private fun logIfSessionExisted(date: String, time: String) = getSessionId(time, date)?.let {
        analytics.logSessionBookmarkRemoved(time, date, it)
    }


}
