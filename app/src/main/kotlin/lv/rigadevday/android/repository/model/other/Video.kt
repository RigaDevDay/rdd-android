package lv.rigadevday.android.repository.model.other

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Video(
    val title: String = "",
    val speaker: String = "",
    val thumbnail: String = "",
    val youtubeid: String = ""
)
