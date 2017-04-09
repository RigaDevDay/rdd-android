package lv.rigadevday.android.utils

import android.text.Html
import android.text.Spanned
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

fun String.toExtraKey() = "lv.rigadevday.android.extra.$this"

fun String.toImageUrl() =
    if (this.startsWith("http")) this
    else "http://rigadevdays.lv${this.replace("..", "")}"

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned = this
    .replace("</br>", "<br/>")
    .let { Html.fromHtml(it) }

fun String.urlEncoded(): String {
    try {
        return URLEncoder.encode(this, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        throw RuntimeException("URLEncoder.encode() failed for " + this)
    }
}

val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
