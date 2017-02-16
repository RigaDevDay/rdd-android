package lv.rigadevday.android.utils

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers


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
