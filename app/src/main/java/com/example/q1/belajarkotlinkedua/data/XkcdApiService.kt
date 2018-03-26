package com.example.q1.belajarkotlinkedua.data

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by q1 on 26/03/18.
 */

interface XkcdApiService {

    @GET("{number}/info.0.json ")
    fun getComicByNumber(@Path("number") number: String): Observable<XkcdData>

    @GET("info.0.json ")
    fun getLatestComic() : Observable<XkcdData>

    companion object {
        fun create() : XkcdApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://xkcd.com/")
                    .build()

            return retrofit.create(XkcdApiService::class.java)
        }
    }
}
