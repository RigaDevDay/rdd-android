package lv.rigadevday.android.repository

import lv.rigadevday.android.repository.model.Root
import lv.rigadevday.android.repository.model.other.Venue
import lv.rigadevday.android.repository.model.partners.Partners
import lv.rigadevday.android.repository.model.schedule.Schedule
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.speakers.Speaker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataCache @Inject constructor() {

    var partners: List<Partners> = emptyList()
    var venues: List<Venue> = emptyList()
    var resources: Map<String, String> = emptyMap()

    var speakers: Map<Int, Speaker> = emptyMap()
    var sessions: Map<Int, Session> = emptyMap()
    var schedule: Map<String, Schedule> = emptyMap()

    fun update(newData: Root): DataCache {
        partners = newData.partners.filterNotNull()
        venues = newData.venues.filterNotNull()
        resources = newData.resources.filterNot { it.key.isNullOrEmpty() || it.value.isNullOrEmpty() }

        speakers = newData.speakers.filterNotNull().associate { it.id to it }
        sessions = newData.sessions.filterNot { it.key.isNullOrEmpty() }.mapKeys { (key, _) -> key.toInt() }
        schedule = newData.schedule.filterNotNull().associate { it.date to it }

        sessions.forEach { (_, session) ->
            // enrich sessions with speakers
            session.speakerObjects = session.speakers
                .map { speakers.getValue(it) }
                .toMutableList()
        }

        schedule.forEach { (date, day) ->
            day.timeslots.forEach { timeslot ->
                // enrich schedule with sessions
                timeslot.sessionObjects = timeslot.sessionIds.map { sessions.getValue(it) }

                // enrich session with info about timeslot
                timeslot.sessionObjects.forEachIndexed { index, session ->
                    session.time = timeslot.startTime
                    session.date = date
                    session.room = day.tracks[index].title
                }
            }
        }

        // enrich partner groups with actual titles
        partners.forEach { it.actualTitle = resources[it.title] ?: it.title }
        return this
    }

}
