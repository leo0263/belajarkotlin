package com.example.q1.belajarkotlinkedua

import com.example.q1.belajarkotlinkedua.domain.GetComicById
import com.example.q1.belajarkotlinkedua.domain.GetLatestComic
import com.example.q1.belajarkotlinkedua.domain.XkcdDataResult
import com.example.q1.belajarkotlinkedua.presentation.MainViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by q1 on 26/03/18.
 */

class MainViewModel(private val getLatestComic: GetLatestComic, private val getComicById: GetComicById) {

    var currentIndex: Int = 0
    var latestIndex: Int = 0
    private val notifySubject : PublishSubject<MainViewState> = PublishSubject.create()

    fun getNotifyObservable() : Observable<MainViewState> = notifySubject

    fun refreshComic() {
        getLatestComic()
    }

    fun gotoPreviousComic() {
        if (canGoPrev(currentIndex)) {
            currentIndex--
            getComicById(currentIndex)
        }
    }

    fun gotoNextComic() {
        if (canGoNext(currentIndex)) {
            currentIndex++
            getComicById(currentIndex)
        }
    }

    private fun canGoNext(index: Int) : Boolean {
        return index < latestIndex
    }

    private fun canGoPrev(index: Int) : Boolean {
        return index > 1
    }

    private fun getLatestComic() {
        getLatestComic.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val mainViewState = MainViewState(it, canGoNext(currentIndex), canGoPrev(currentIndex))
                    currentIndex = mainViewState.data.num
                    latestIndex = mainViewState.data.num
                    notifySubject.onNext(mainViewState)
                }
    }

    private fun getComicById(number: Int) {
        getComicById.execute(number.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val mainViewState = MainViewState(it, canGoNext(currentIndex), canGoPrev(currentIndex))
                    notifySubject.onNext(mainViewState)
                }
    }

}