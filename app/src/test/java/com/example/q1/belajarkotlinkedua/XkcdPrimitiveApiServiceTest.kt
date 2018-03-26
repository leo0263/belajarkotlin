package com.example.q1.belajarkotlinkedua

import com.example.q1.belajarkotlinkedua.data.XkcdApiService
import org.junit.Test

/**
 * Created by q1 on 26/03/18.
 */

class XkcdPrimitiveApiServiceTest {

    @Test
    fun test_xkcd_api_get_latest() {
        val xkcdApiService: XkcdApiService = XkcdApiService.create()
        xkcdApiService.getLatestComic().subscribe { print(it) }
    }

    @Test
    fun test_xkcd_api_get_by_number() {
        val xkcdApiService: XkcdApiService = XkcdApiService.create()
        xkcdApiService.getComicByNumber("1").subscribe { print(it) }
    }

}