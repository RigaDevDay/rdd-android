package lv.rigadevday.android.repository

import com.google.firebase.database.*
import io.reactivex.Observable
import lv.rigadevday.android.repository.model.partners.Partners
import lv.rigadevday.android.repository.model.schedule.Schedule
import lv.rigadevday.android.repository.model.schedule.Session
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.repository.model.team.Team

/**
 * All of the observables provided by repository are non-closable so it is mandatory
 * to unsubscribe any subscription when closing screen to prevent memory leak.
 */
class Repository {

    val database: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }

    fun speakers(): Observable<List<Speaker>> = getListObservable("speakers", Speaker::class.java)

    fun speaker(id: Int): Observable<Speaker> = getSingleObservable("speakers", id, Speaker::class.java)

    fun schedule(): Observable<List<Schedule>> = getListObservable("schedule", Schedule::class.java)

    fun scheduleDay(index: Int): Observable<Schedule> = getSingleObservable("schedule", index, Schedule::class.java)

    fun session(id: Int): Observable<Session> = getSingleObservable("sessions", id, Session::class.java)

    fun team(): Observable<List<Team>> = getListObservable("team", Team::class.java)

    fun partners(): Observable<List<Partners>> = getListObservable("partners", Partners::class.java)


    private fun <T> getListObservable(table: String, klass: Class<T>): Observable<List<T>> {
        return Observable.create { emitter ->
            database.child(table).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    emitter.onError(p0?.toException())
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    val list = p0?.children?.map { it.getValue(klass) }
                    emitter.onNext(list)
                }

            })
        }
    }

    private fun <T> getSingleObservable(table: String, id: Int, klass: Class<T>): Observable<T> {
        return Observable.create { emitter ->
            database.child(table).child("$id").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    emitter.onError(p0?.toException())
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    emitter.onNext(p0?.getValue(klass))
                }

            })
        }
    }

}
