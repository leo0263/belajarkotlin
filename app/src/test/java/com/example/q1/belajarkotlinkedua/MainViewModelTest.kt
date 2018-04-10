package com.example.q1.belajarkotlinkedua

import android.support.annotation.NonNull
import com.example.q1.belajarkotlinkedua.domain.GetComicById
import com.example.q1.belajarkotlinkedua.domain.GetLatestComic
import com.example.q1.belajarkotlinkedua.domain.XkcdDataResult
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.Scheduler.Worker
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import org.junit.BeforeClass
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before


class MainViewModelTest {

    val getComicById = mock<GetComicById>()
    val getLatestComic = mock<GetLatestComic>()
    val dummyXkcdDataResult = XkcdDataResult(
            1,
            666,
            "dummy link",
            2018,
            "dummy news",
            "dummy safe title",
            "dummy transcript",
            "dummy alt",
            "dummy img",
            "dummy title",
            1
        )

    // workaround to mock Retrofit service call
    //      https://stackoverflow.com/a/40018295
    // workaround to .observeOn(AndroidSchedulers.mainThread()) error when called by mockito
    //      https://stackoverflow.com/a/46702826
    @Before
    fun setUpRxSchedulers() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    @Test
    fun try_mocking_domains() {

        whenever(getLatestComic.execute()).thenReturn(Observable.just(dummyXkcdDataResult))
        whenever(getComicById.execute(anyString())).thenReturn(Observable.just(dummyXkcdDataResult))

        var mainViewModel = MainViewModel(getLatestComic, getComicById)

        mainViewModel.refreshComic()
        Thread.sleep(500)
        Assert.assertEquals(mainViewModel.currentIndex, 666)

        mainViewModel.gotoNextComic()
        Thread.sleep(500)
        Assert.assertEquals(mainViewModel.currentIndex, 666)

        mainViewModel.gotoPreviousComic()
        Thread.sleep(500)
        Assert.assertEquals(mainViewModel.currentIndex, 665)
    }

}