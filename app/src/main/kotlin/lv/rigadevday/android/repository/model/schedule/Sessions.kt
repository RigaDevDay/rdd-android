package lv.rigadevday.android.repository.model.schedule

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Sessions(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = ""
)
