package com.example.q1.belajarkotlinkedua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.q1.belajarkotlinkedua.data.XkcdApiService
import com.example.q1.belajarkotlinkedua.data.XkcdData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var xkcdApiService : XkcdApiService
    private lateinit var xkcdData : XkcdData

    private lateinit var testText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testText = findViewById(R.id.text_test)

        initRetrofit()
        getLatestComic()
    }

    private fun initRetrofit() {
        xkcdApiService = XkcdApiService.create()
    }


    private fun getLatestComic() {
        xkcdApiService.getLatestComic()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {  xkcdData = it; updateUI() },
                        { Log.e("MainActivity", it.toString()) }
                )
    }

    private fun updateUI() {
        testText.text = xkcdData.safe_title
    }

}
