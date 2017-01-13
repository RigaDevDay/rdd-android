package lv.rigadevday.android.repository

import com.google.firebase.database.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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

    private val database: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }

    fun speakers(): Single<List<Speaker>> = getListObservable("speakers", Speaker::class.java).bindSchedulers()

    fun speaker(id: Int): Single<Speaker> = getSingleObservable("speakers", id, Speaker::class.java).bindSchedulers()

    fun schedule(): Single<List<Schedule>> = getListObservable("schedule", Schedule::class.java).bindSchedulers()

    fun scheduleDay(index: Int): Single<Schedule> = getSingleObservable("schedule", index, Schedule::class.java).bindSchedulers()

    fun session(id: Int): Single<Session> = getSingleObservable("sessions", id, Session::class.java).bindSchedulers()

    fun team(): Single<List<Team>> = getListObservable("team", Team::class.java).bindSchedulers()

    fun partners(): Single<List<Partners>> = getListObservable("partners", Partners::class.java).bindSchedulers()


    private fun <T> getListObservable(table: String, klass: Class<T>): Single<List<T>> = Single.create { emitter ->
        database.child(table).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                emitter.onError(p0?.toException())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val list = p0?.children?.map { it.getValue(klass) }
                emitter.onSuccess(list)
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

}

private fun <T> Single<T>.bindSchedulers(): Single<T> = this
    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
