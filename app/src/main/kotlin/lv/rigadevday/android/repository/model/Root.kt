package lv.rigadevday.android.repository.model

import lv.rigadevday.android.repository.model.partners.Partners
import lv.rigadevday.android.repository.model.schedule.Schedule
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.repository.model.team.Team

data class Root(
    val schedule: List<Schedule> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val speakers: List<Speaker> = emptyList(),
    val team: List<Team> = emptyList(),
    val partners: List<Partners> = emptyList()
)
