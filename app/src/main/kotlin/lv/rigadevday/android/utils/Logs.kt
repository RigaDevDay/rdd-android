package lv.rigadevday.android.utils

import android.util.Log

const val TAG = "RDD_TAG"

fun String?.logD() = Log.d(TAG, this)
fun Any?.logD() = Log.d(TAG, this.toString())

fun String?.logE() = Log.e(TAG, this)
fun Any?.logE() = Log.e(TAG, this.toString())
