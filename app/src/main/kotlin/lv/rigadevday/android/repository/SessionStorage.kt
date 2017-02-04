package lv.rigadevday.android.repository

import android.content.Context
import android.content.SharedPreferences

class SessionStorage(context: Context) {

    companion object {
        private val PREF_NAME = "saved_sessions"
        private val KEY = "%s_%s"
    }

    private val preference: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(time: String, date: String, sessionId: Int) {
        preference.edit().putInt(KEY.format(time, date), sessionId).apply()
    }

    fun getSessionId(time: String, date: String): Int? {
        val key = KEY.format(time, date)
        val id = preference.getInt(key, -1)
        return if (id >= 0) id else null
    }

    fun removeSession(time: String, date: String) {
        preference.edit().remove(KEY.format(time, date)).apply()
    }

}
