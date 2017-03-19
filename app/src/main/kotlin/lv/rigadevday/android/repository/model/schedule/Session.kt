package lv.rigadevday.android.repository.model.schedule

import com.google.firebase.database.IgnoreExtraProperties
import lv.rigadevday.android.repository.ColorStorage
import lv.rigadevday.android.repository.model.speakers.Speaker

@IgnoreExtraProperties
data class Session(
    val id: Int = -1,

    val title: String = "",
    val description: String = "",
    val speakers: List<Int> = emptyList(),
    val tags: List<String> = emptyList(),

    val image: String = "",
    val complexity: String = ""
) {
    var speakerObjects: MutableList<Speaker> = mutableListOf()
    var room: String = ""

    var time: String = ""
    var date: String = ""

    var rating: Rating = Rating()

    val complexityAndTags: String
        get() = "$complexity / ${tags.joinToString()}"

    val color: Int
        get() = ColorStorage.get(tags.firstOrNull() ?: "")

    companion object {
        val TBD = Session(
            title = "TBD",
            description = "TBD"
        )
    }
}

