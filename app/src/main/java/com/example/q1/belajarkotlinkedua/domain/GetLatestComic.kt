package com.example.q1.belajarkotlinkedua.domain

import com.example.q1.belajarkotlinkedua.data.XkcdApiService
import io.reactivex.Observable

/**
 * Created by q1 on 26/03/18.
 */

class GetLatestComic(private val xkcdApiService: XkcdApiService) {

    fun execute() : Observable<XkcdDataResult> {
        return xkcdApiService.getLatestComic().map {
            XkcdDataResult.fromRemoteResult( it )
        }
    }

}