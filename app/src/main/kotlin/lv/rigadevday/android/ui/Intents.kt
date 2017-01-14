package lv.rigadevday.android.ui

import android.content.Context
import android.content.Intent
import lv.rigadevday.android.ui.speakers.speaker.SpeakerActivity
import lv.rigadevday.android.utils.toExtraKey

val EXTRA_SPEAKER_ID = "speaker_id".toExtraKey()

fun Context.openSpeakerActivity(id: Int) {
    Intent(this, SpeakerActivity::class.java).apply {
        putExtra(EXTRA_SPEAKER_ID, id)
    }.start(from = this)
}

fun Intent.start(from: Context) {
    from.startActivity(this)
}
