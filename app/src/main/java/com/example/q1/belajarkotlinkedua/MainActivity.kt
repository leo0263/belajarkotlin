package com.example.q1.belajarkotlinkedua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.q1.belajarkotlinkedua.data.XkcdApiService
import com.example.q1.belajarkotlinkedua.data.XkcdData
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.flowable.FlowableReplay.observeOn
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var xkcdApiService : XkcdApiService
    private lateinit var xkcdData : XkcdData
    private var latestIndex: Int = 0
    private var currentIndex: Int = 0

    private lateinit var comicTitle : TextView
    private lateinit var comicImage : ImageView
    private lateinit var nextButton : Button
    private lateinit var prevButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        comicTitle = findViewById(R.id.tv_comic_title)
        comicImage = findViewById(R.id.iv_comic_image)
        nextButton = findViewById(R.id.bt_next_comic)
        prevButton = findViewById(R.id.bt_previous_comic)

        nextButton.setOnClickListener { pressNextButton() }
        prevButton.setOnClickListener { pressPrevButton() }

        initRetrofit()
        getLatestComic()
    }

    private fun pressPrevButton() {
        var prevIndex = currentIndex - 1
        xkcdApiService.getComicByNumber(prevIndex.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {   xkcdData = it
                            updateUI() },
                        { Log.e("MainActivity", it.toString()) }
                )
    }

    private fun pressNextButton() {
        var prevIndex = currentIndex + 1
        xkcdApiService.getComicByNumber(prevIndex.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {   xkcdData = it
                            updateUI() },
                        { Log.e("MainActivity", it.toString()) }
                )
    }

    private fun initRetrofit() {
        xkcdApiService = XkcdApiService.create()
    }


    private fun getLatestComic() {
        xkcdApiService.getLatestComic()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {   xkcdData = it
                            latestIndex = xkcdData.num
                            updateUI() },
                        { Log.e("MainActivity", it.toString()) }
                )
    }

    private fun updateUI() {
        comicTitle.text = xkcdData.safe_title
        Picasso.with(this)
                .load(xkcdData.img).fit().centerInside()
                .into(comicImage)

        currentIndex = xkcdData.num
        nextButton.isEnabled = currentIndex < latestIndex
        prevButton.isEnabled = currentIndex >= 1

    }

}
