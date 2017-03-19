package lv.rigadevday.android.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import lv.rigadevday.android.repository.model.other.Venue
import lv.rigadevday.android.repository.model.partners.Partners
import lv.rigadevday.android.repository.model.schedule.Rating
import lv.rigadevday.android.repository.model.schedule.Schedule
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.schedule.Timeslot
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.utils.asFlowable
import lv.rigadevday.android.utils.auth.AuthStorage
import lv.rigadevday.android.utils.biFunction
import lv.rigadevday.android.utils.bindSchedulers


/**
 * All of the observables provided by repository are non-closable so it is mandatory
 * to unsubscribe any subscription when closing screen to prevent memory leak.
 */
class Repository(val authStorage: AuthStorage) {

    private val database: DatabaseReference by lazy {
        val ref = FirebaseDatabase.getInstance().reference
        ref.keepSynced(true)
        return@lazy ref
    }

    // Basic requests
    fun speakers(): Flowable<Speaker> = getFlowable("speakers", Speaker::class.java).bindSchedulers()

    fun speaker(id: Int): Maybe<Speaker> = getSingle("speakers", id, Speaker::class.java).bindSchedulers()

    fun schedule(): Flowable<Schedule> = getFlowable("schedule", Schedule::class.java).bindSchedulers()

    fun partners(): Flowable<Partners> = getFlowable("partners", Partners::class.java).bindSchedulers()

    fun venues(): Flowable<Venue> = getFlowable("venues", Venue::class.java).bindSchedulers()

    fun venue(id: Int): Maybe<Venue> = getSingle("venues", id, Venue::class.java).bindSchedulers()

    fun cacheResources(): Completable = RxFirebaseDatabase.observeSingleValueEvent(
        database.child("resources"),
        DataSnapshotMapper.mapOf(String::class.java)
    ).flatMapCompletable {
        ResourceCache.update(it)
        Completable.complete()
    }

    // More complicated requests
    fun sessions(): Flowable<Session> = getFlowable("sessions", Session::class.java)
        .map { session ->
            session.speakers.asFlowable()
                .concatMap { speaker(it).toFlowable() }
                .toList()
                .map { session.apply { speakerObjects = it } }
        }
        .flatMap { it.toFlowable() }
        .bindSchedulers()

    fun session(id: Int): Maybe<Session> = getSingle("sessions", id, Session::class.java)
        .flatMap { session ->
            session.speakers.asFlowable()
                .concatMap { speaker(it).toFlowable() }.toList()
                .map { session.apply { speakerObjects = it } }
                .toMaybe()
        }
        .bindSchedulers()

    fun scheduleDayTimeslots(dateCode: String): Flowable<Timeslot> = schedule()
        .filter { it.date == dateCode }
        .firstElement()
        .map {
            val roomNames = it.tracks.map { it.title }.asFlowable()
            val allSessions = sessions()

            it.timeslots.asFlowable().concatMap { timeslot ->
                timeslot.sessionIds.asFlowable()
                    .concatMap { id -> allSessions.filter { it.id == id }.firstElement().toFlowable() }
                    .zipWith(roomNames, biFunction { session, title -> session.apply { room = title } })
                    .toList()
                    .map { timeslot.apply { sessionObjects = it } }
                    .toFlowable()
            }
        }
        .flatMapPublisher { it }
        .bindSchedulers()

    // Helper functions
    private fun <T> getFlowable(table: String, klass: Class<T>) = RxFirebaseDatabase
        .observeSingleValueEvent(
            database.child(table),
            DataSnapshotMapper.listOf(klass)
        )
        .flatMap { it.asFlowable() }

    private fun <T> getSingle(table: String, id: Int, klass: Class<T>) = RxFirebaseDatabase
        .observeSingleValueEvent(database.child(table).child("$id"), klass)
        .firstElement()

    // Read-Write stuff
    private val sessionRating = database.child("userFeedbacks").child(authStorage.uId)

    fun rating(sessionId: Int): Maybe<Rating> = if (authStorage.hasLogin) {
        RxFirebaseDatabase.observeSingleValueEvent(
            sessionRating.child(sessionId.toString()),
            Rating::class.java
        ).firstElement().onErrorReturn { Rating() }
    } else Maybe.just(Rating())

    fun saveRating(sessionId: Int, rating: Rating) {
        if (authStorage.hasLogin) {
            sessionRating.child(sessionId.toString()).setValue(rating)
        }
    }
}
