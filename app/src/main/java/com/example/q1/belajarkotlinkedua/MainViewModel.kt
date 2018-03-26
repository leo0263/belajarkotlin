package com.example.q1.belajarkotlinkedua

import com.example.q1.belajarkotlinkedua.domain.GetComicById
import com.example.q1.belajarkotlinkedua.domain.GetLatestComic
import com.example.q1.belajarkotlinkedua.domain.XkcdDataResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 * Created by q1 on 26/03/18.
 */

class MainViewModel(private val getLatestComic: GetLatestComic,private val getComicById: GetComicById) {

    private val notifySubject : PublishSubject<Boolean> = PublishSubject.create()
    var xkcdData : XkcdDataResult? = null
    var latestIndex : Int = 0

    fun getNotifyObservable() : Observable<Boolean> = notifySubject

    fun getLatestComic() {
        getLatestComic.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    xkcdData = it
                    latestIndex = it.num
                    notifySubject.onNext(true)
                }
    }

    fun getComicById(number: String) {
        getComicById.execute(number)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    xkcdData = it
                    notifySubject.onNext(true)
                }
    }

}