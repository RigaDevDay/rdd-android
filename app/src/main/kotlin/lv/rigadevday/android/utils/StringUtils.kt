package lv.rigadevday.android.utils

fun String.toExtraKey() = "lv.rigadevday.android.extra.$this"

fun String.prependDomain() = "http://rigadevdays.lv$this"
