package lv.rigadevday.android.repository

import android.graphics.Color

object ColorStorage {

    private fun String.asColor() = Color.parseColor(this)

    private val DEFAULT = "#bdbdbd".asColor()

    private val COLORS = mapOf(
        "general" to "#bdbdbd".asColor(),
        "mobile" to "#ff8a80".asColor(),
        "design" to "#9c27b0".asColor(),
        "kotlin" to "#2196f3".asColor(),
        "ios" to "#607d8b".asColor(),
        "android" to "#78c257".asColor(),
        "ci" to "#cddc39".asColor(),
        "firebase" to "#ff9800".asColor()
    )

    fun get(tag: String) = COLORS[tag.toLowerCase().replace(' ', '-')] ?: DEFAULT

}
