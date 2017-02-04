package lv.rigadevday.android.repository.model.other

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Venue(
    val title: String = "",
    val name: String = "",
    val address: String = "",
    val web: String = "",
    val email: String = "",
    val description: String = "",
    val coordinates: String = ""
)
