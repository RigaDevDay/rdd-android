package lv.rigadevday.android.utils

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import lv.rigadevday.android.BuildConfig
import lv.rigadevday.android.R

fun BaseApp.setupImageLoading() = Picasso.setSingletonInstance(
    Picasso.Builder(this)
        .downloader(OkHttp3Downloader(this))
        .build()
        .apply { setIndicatorsEnabled(BuildConfig.DEBUG) }
)

private fun ImageView.getPicassoInstance(url: String) = Picasso.with(this.context)
    .load(url.toImageUrl())
    .fit()

fun ImageView.loadImage(
    url: String,
    @DrawableRes placeholder: Int = R.drawable.vector_speaker_placeholder
) = getPicassoInstance(url)
    .placeholder(placeholder)
    .centerCrop()
    .into(this)


fun ImageView.loadLogo(url: String, onDone: () -> Unit) = getPicassoInstance(url)
    .centerInside()
    .into(this, object : Callback {
        override fun onSuccess() {
            onDone()
        }

        override fun onError() {}
    })


fun ImageView.loadVenueImage(url: String) = getPicassoInstance(url)
    .centerCrop()
    .into(this)
