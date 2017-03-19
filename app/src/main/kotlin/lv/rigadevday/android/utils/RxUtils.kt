package lv.rigadevday.android.utils

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import lv.rigadevday.android.repository.Repository


fun <T> Iterable<T>.asFlowable(): Flowable<T> = Flowable.fromIterable<T>(this)

fun <T> T.asFlowable(): Flowable<T> = Flowable.just<T>(this)

fun <T> Maybe<T>.bindSchedulers(): Maybe<T> = this
    .cache()
    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.bindSchedulers(): Flowable<T> = this
    .cache()
    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T, R> biFunction(function: (T, R) -> T): BiFunction<T, R, T> = BiFunction(function)


fun findSessionsMismatches(repo: Repository) = repo.schedule()
    .flatMap { Flowable.fromIterable(it.timeslots) }
    .flatMap { Flowable.fromIterable(it.sessionIds) }
    .toList()
    .map { it.distinct() }
    .zipWith(repo.sessions().map { it.id }.toList(), biFunction { idsInSchedule, idsInSessions ->
        "ids in sessions and not in schedule: ".logE()
        idsInSessions.subtract(idsInSchedule).logD()

        "ids in schedule and not in sessions: ".logE()
        idsInSchedule.subtract(idsInSessions).logD()

        listOf<Int>()
    })
    .toCompletable()

