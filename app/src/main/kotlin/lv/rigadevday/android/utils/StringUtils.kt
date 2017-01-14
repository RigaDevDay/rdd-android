package lv.rigadevday.android.utils

import android.text.Html
import android.text.Spanned

fun String.toExtraKey() = "lv.rigadevday.android.extra.$this"

fun String.prependDomain() = "http://rigadevdays.lv$this"

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned = Html.fromHtml(this)
