package lv.rigadevday.android.repository.model.other

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Blog(
    val id: String = "",
    val title: String = "",

    val posted: String = "",
    val brief: String = "",
    val image: String = "",

    val primaryColor: String = "",
    val secondaryColor: String = ""
)
