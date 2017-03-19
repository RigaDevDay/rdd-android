package lv.rigadevday.android.utils

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction


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
