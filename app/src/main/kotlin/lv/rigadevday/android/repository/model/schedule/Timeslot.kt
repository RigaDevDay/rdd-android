package lv.rigadevday.android.repository.model.schedule

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Timeslot(
    val startTime: String = "",
    val endTime: String = "",
    val sessions: List<Int> = emptyList()
)
