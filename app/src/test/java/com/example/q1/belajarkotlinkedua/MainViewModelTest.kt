package com.example.q1.belajarkotlinkedua

import com.example.q1.belajarkotlinkedua.domain.GetComicById
import com.example.q1.belajarkotlinkedua.domain.GetLatestComic
import com.example.q1.belajarkotlinkedua.domain.XkcdDataResult
import com.example.q1.belajarkotlinkedua.presentation.MainViewState
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before


class MainViewModelTest {

    private val getComicById = mock<GetComicById>()
    private val getLatestComic = mock<GetLatestComic>()
    private lateinit var mainViewModel: MainViewModel

    private val dummyXkcdDataResult = XkcdDataResult(
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
    @Before
    fun initialTestSetup() {
        whenever(getLatestComic.execute()).thenReturn(Observable.just(dummyXkcdDataResult))
        whenever(getComicById.execute(anyString())).thenReturn(Observable.just(dummyXkcdDataResult))
        mainViewModel = MainViewModel(getLatestComic, getComicById, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun try_mocking_domains() {

        mainViewModel.refreshComic()
        Assert.assertEquals(mainViewModel.currentIndex, 666)

        mainViewModel.gotoNextComic()
        Assert.assertEquals(mainViewModel.currentIndex, 666)

        mainViewModel.gotoPreviousComic()
        Assert.assertEquals(mainViewModel.currentIndex, 665)

        mainViewModel.gotoPreviousComic()
        Assert.assertEquals(mainViewModel.currentIndex, 664)

        mainViewModel.gotoNextComic()
        Assert.assertEquals(mainViewModel.currentIndex, 665)

        mainViewModel.refreshComic()
        Assert.assertEquals(mainViewModel.currentIndex, 666)
    }

    @Test
    fun refreshComic_shouldEmit_MainViewState() {

        var mainViewState: MainViewState

        val testSubscriber = mainViewModel.getNotifyObservable()
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.trampoline())
                .test()

        mainViewModel.refreshComic()

        mainViewState = MainViewState(dummyXkcdDataResult, false, true)

        testSubscriber.assertValue(mainViewState)
        testSubscriber.assertNotComplete()
    }

}