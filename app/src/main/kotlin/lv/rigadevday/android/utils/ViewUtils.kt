package lv.rigadevday.android.utils

import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import lv.rigadevday.android.R

fun ViewGroup.inflate(@LayoutRes id: Int): View
    = LayoutInflater.from(this.context).inflate(id, this, false)

fun ImageView.loadImage(url: String, @DrawableRes placeholder: Int = R.drawable.vector_speaker_placeholder) {
    Picasso.with(this.context)
        .load(url.prependDomain())
        .placeholder(placeholder)
        .fit()
        .centerCrop()
        .into(this)
}

fun ImageView.loadLogo(url: String) {
    Picasso.with(this.context)
        .load(url.prependDomain())
        .resize(120, 120)
        .centerInside()
        .into(this)
}

fun View.unhide() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showMessage(@StringRes stringId: Int) {
    Toast.makeText(this.context, stringId, Toast.LENGTH_SHORT).show()
}
