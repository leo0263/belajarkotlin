package com.example.q1.belajarkotlinkedua.domain

import com.example.q1.belajarkotlinkedua.data.XkcdDataRemoteResult

/**
 * Created by q1 on 26/03/18.
 */

data class XkcdDataResult(
        val month : Int,
        val num : Int,
        val link : String,
        val year : Int,
        val news : String,
        val safe_title : String,
        val transcript : String,
        val alt : String,
        val img : String,
        val title : String,
        val day : Int
) {
    companion object {
        fun fromRemoteResult(xkcdDataRemoteResult: XkcdDataRemoteResult) : XkcdDataResult {
            return XkcdDataResult(
                    xkcdDataRemoteResult.month,
                    xkcdDataRemoteResult.num,
                    xkcdDataRemoteResult.link,
                    xkcdDataRemoteResult.year,
                    xkcdDataRemoteResult.news,
                    xkcdDataRemoteResult.safe_title,
                    xkcdDataRemoteResult.transcript,
                    xkcdDataRemoteResult.alt,
                    xkcdDataRemoteResult.img,
                    xkcdDataRemoteResult.title,
                    xkcdDataRemoteResult.day)
        }
    }
}