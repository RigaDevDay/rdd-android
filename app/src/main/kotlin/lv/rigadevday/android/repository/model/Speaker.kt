package lv.rigadevday.android.repository.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Speaker(
    var id: Int = -1,
    var featured: Boolean = false,

    var name: String = "",
    var shortBio: String = "",
    var bio: String = "",
    var photoUrl: String = "",

    var title: String = "",
    var company: String = "",
    var country: String = "",

    var badges: List<Badge> = emptyList(),
    var socials: List<SocialAccount> = emptyList(),
    var tags: List<String> = emptyList()
)

@IgnoreExtraProperties
data class Badge(
    var name: String = "",
    var description: String = "",
    var link: String = ""
)

@IgnoreExtraProperties
data class SocialAccount(
    var name: String = "",
    var icon: String = "",
    var link: String = ""
)
