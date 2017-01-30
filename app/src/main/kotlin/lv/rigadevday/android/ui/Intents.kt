package lv.rigadevday.android.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import lv.rigadevday.android.ui.schedule.SessionDetailsActivity
import lv.rigadevday.android.ui.schedule.TimeslotData
import lv.rigadevday.android.ui.schedule.sessions.SessionsActivity
import lv.rigadevday.android.ui.schedule.toBundle
import lv.rigadevday.android.ui.speakers.SpeakerDialogActivity
import lv.rigadevday.android.utils.toExtraKey

val EXTRA_SPEAKER_ID = "speaker_id".toExtraKey()
val EXTRA_SESSION_DATA = "session_data".toExtraKey()
val EXTRA_SESSION_ID = "session_id".toExtraKey()

fun Intent.start(from: Context) {
    from.startActivity(this)
}

fun Context.openSpeakerActivity(id: Int) {
    Intent(this, SpeakerDialogActivity::class.java).apply {
        putExtra(EXTRA_SPEAKER_ID, id)
    }.start(from = this)
}

fun Context.openSessionsActivity(data: TimeslotData? = null) {
    Intent(this, SessionsActivity::class.java).apply {
        data?.run { putExtra(EXTRA_SESSION_DATA, toBundle()) }
    }.start(from = this)
}

fun Context.openSessionDetailsActivity(sessionId: Int) {
    Intent(this, SessionDetailsActivity::class.java).apply {
        putExtra(EXTRA_SESSION_ID, sessionId)
    }.start(from = this)
}

fun Context.openWeb(link: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }.start(this)
}
