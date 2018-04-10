package com.example.q1.belajarkotlinkedua

import com.example.q1.belajarkotlinkedua.domain.GetComicById
import com.example.q1.belajarkotlinkedua.domain.GetLatestComic
import com.example.q1.belajarkotlinkedua.domain.XkcdDataResult
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

    @Test
    fun try_mocking_domains() {

        whenever(getLatestComic.execute()).thenReturn(Observable.just(dummyXkcdDataResult))
        whenever(getComicById.execute(anyString())).thenReturn(Observable.just(dummyXkcdDataResult))

        var mainViewModel = MainViewModel(getLatestComic, getComicById, Schedulers.trampoline(), Schedulers.trampoline())

        mainViewModel.refreshComic()
        Assert.assertEquals(mainViewModel.currentIndex, 666)

        mainViewModel.gotoNextComic()
        Assert.assertEquals(mainViewModel.currentIndex, 666)

        mainViewModel.gotoPreviousComic()
        Assert.assertEquals(mainViewModel.currentIndex, 665)
    }

}