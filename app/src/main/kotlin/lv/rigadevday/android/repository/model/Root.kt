package lv.rigadevday.android.repository.model

import com.google.firebase.database.IgnoreExtraProperties
import lv.rigadevday.android.repository.model.other.Venue
import lv.rigadevday.android.repository.model.partners.Partners
import lv.rigadevday.android.repository.model.schedule.Schedule
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.speakers.Speaker

@IgnoreExtraProperties
data class Root(

    val partners: List<Partners> = emptyList(),
    val venues: List<Venue> = emptyList(),
    val speakers: List<Speaker> = emptyList(),

    val resources: Map<String, String> = emptyMap(),
    val sessions: Map<String, Session> = emptyMap(),
    val schedule: List<Schedule> = emptyList()
)
