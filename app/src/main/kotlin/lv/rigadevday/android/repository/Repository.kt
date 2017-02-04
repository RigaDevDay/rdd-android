package lv.rigadevday.android.repository

import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import lv.rigadevday.android.repository.model.partners.Partners
import lv.rigadevday.android.repository.model.schedule.Schedule
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.schedule.Timeslot
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.repository.model.team.Team

/**
 * All of the observables provided by repository are non-closable so it is mandatory
 * to unsubscribe any subscription when closing screen to prevent memory leak.
 */
class Repository {

    private val database: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }

    // Basic requests
    fun speakers(): Observable<Speaker> = getObservable("speakers", Speaker::class.java).bindSchedulers()

    fun speaker(id: Int): Single<Speaker> = getSingleObservable("speakers", id, Speaker::class.java).bindSchedulers()

    fun schedule(): Observable<Schedule> = getObservable("schedule", Schedule::class.java).bindSchedulers()

    fun team(): Observable<Team> = getObservable("team", Team::class.java).bindSchedulers()

    fun partners(): Observable<Partners> = getObservable("partners", Partners::class.java).bindSchedulers()


    // More complicated requests
    fun sessions(): Observable<Session> = getObservable("sessions", Session::class.java)
        .map { session ->
            Observable.fromIterable(session.speakers)
                .concatMap { speaker(it).toObservable() }.toList()
                .map { session.apply { speakerObjects = it } }
        }
        .flatMap { it.toObservable() }
        .bindSchedulers()

    fun session(id: Int): Single<Session> = getSingleObservable("sessions", id, Session::class.java)
        .flatMap { session ->
            Observable.fromIterable(session.speakers)
                .concatMap { speaker(it).toObservable() }.toList()
                .map { session.apply { speakerObjects = it } }
        }
        .bindSchedulers()


    fun scheduleDay(dateCode: String): Single<Schedule> {
        return Single.create<Schedule> { emitter ->
            database.child("schedule").orderByChild("date").equalTo(dateCode).limitToFirst(1).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    emitter.onError(p0?.toException())
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    emitter.onSuccess(p0?.children?.first()?.getValue(Schedule::class.java))
                }
            })
        }.bindSchedulers()
    }

    fun scheduleDayTimeslots(dateCode: String): Observable<Timeslot> {
        return scheduleDay(dateCode).map {
            val roomNames = Observable.fromIterable(it.tracks.map { it.title })
            val allSessions = sessions()

            Observable.fromIterable(it.timeslots)
                .concatMap { timeslot ->
                    Observable.fromIterable(timeslot.sessionIds)
                        .concatMap { id -> allSessions.filter { it.id == id }.firstOrError().toObservable() }
                        .zipWith(roomNames, BiFunction<Session, String, Session> { session, title ->
                            session.apply { room = title }
                        })
                        .toList()
                        .map { timeslot.apply { sessionObjects = it } }
                        .toObservable()
                }
        }.flatMapObservable { it }
    }


    // Helper functions
    private fun <T> getObservable(table: String, klass: Class<T>): Observable<T> = Observable.create<T> { emitter ->
        database.child(table).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                emitter.onError(p0?.toException())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                p0?.children?.map { emitter.onNext(it.getValue(klass)) }
                emitter.onComplete()
            }
        })
    }

    private fun <T> getSingleObservable(table: String, id: Int, klass: Class<T>): Single<T> = Single.create { emitter ->
        database.child(table).child("$id").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                emitter.onError(p0?.toException())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                emitter.onSuccess(p0?.getValue(klass))
            }

        })
    }

    private fun <T> Single<T>.bindSchedulers(): Single<T> = this
        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    private fun <T> Observable<T>.bindSchedulers(): Observable<T> = this
        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
