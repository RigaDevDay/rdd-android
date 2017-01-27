package lv.rigadevday.android.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import lv.rigadevday.android.ui.schedule.sessions.SessionsActivity
import lv.rigadevday.android.ui.speakers.SpeakerDialogActivity
import lv.rigadevday.android.utils.toExtraKey
import java.util.*

val EXTRA_SPEAKER_ID = "speaker_id".toExtraKey()
val EXTRA_SESSION_TIMESLOT = "session_timeslot".toExtraKey()
val EXTRA_SESSION_IDS = "session_ids".toExtraKey()

fun Intent.start(from: Context) {
    from.startActivity(this)
}

fun Context.openSpeakerActivity(id: Int) {
    Intent(this, SpeakerDialogActivity::class.java).apply {
        putExtra(EXTRA_SPEAKER_ID, id)
    }.start(from = this)
}

fun Context.openSessionsActivity(data: SessionsActivity.SessionIntentData? = null) {
    Intent(this, SessionsActivity::class.java).apply {
        if (data != null) {
            putStringArrayListExtra(EXTRA_SESSION_TIMESLOT, ArrayList(listOf(data.date, data.dataCode, data.time)))
            putIntegerArrayListExtra(EXTRA_SESSION_IDS, ArrayList(data.ids))
        }
    }.start(from = this)
}

fun Context.openWeb(link: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }.start(this)
}
