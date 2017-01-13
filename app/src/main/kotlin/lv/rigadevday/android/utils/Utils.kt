package lv.rigadevday.android.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

fun String.toExtraKey() = "lv.rigadevday.android.extra.$this"

fun ViewGroup.inflate(@LayoutRes id: Int): View
    = LayoutInflater.from(this.context).inflate(id, this, false)

fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).into(this)
}
