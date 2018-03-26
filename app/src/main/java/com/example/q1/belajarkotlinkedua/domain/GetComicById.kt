package com.example.q1.belajarkotlinkedua.domain

import com.example.q1.belajarkotlinkedua.data.XkcdApiService
import io.reactivex.Observable

/**
 * Created by q1 on 26/03/18.
 */
class GetComicById(private val xkcdApiService: XkcdApiService) {

    fun execute(number: String) : Observable<XkcdDataResult> {
        return xkcdApiService.getComicByNumber(number).map {
            XkcdDataResult.fromRemoteResult( it )
        }
    }

}