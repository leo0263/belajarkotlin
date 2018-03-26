package com.example.q1.belajarkotlinkedua.data

/**
 * Created by q1 on 26/03/18.
 */


//month	"3"
//num	1971
//link	""
//year	"2018"
//news	""
//safe_title	"Personal Data"
//transcript	""
//alt	"Do I just leave money in my mailbox? How much? How much money do they need, anyway? I guess it probably depends how the economy is doing. If stocks go up, should I leave more money in my mailbox or less?"
//img	"https://imgs.xkcd.com/comics/personal_data.png"
//title	"Personal Data"
//day	"23"

data class XkcdData(
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
)