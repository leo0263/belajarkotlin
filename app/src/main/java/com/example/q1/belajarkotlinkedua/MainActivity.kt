package com.example.q1.belajarkotlinkedua

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.q1.belajarkotlinkedua.data.XkcdApiService
import com.example.q1.belajarkotlinkedua.domain.GetComicById
import com.example.q1.belajarkotlinkedua.domain.GetLatestComic
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var currentIndex: Int = 0

    private lateinit var comicTitle : TextView
    private lateinit var comicImage : ImageView
    private lateinit var nextButton : Button
    private lateinit var prevButton : Button

    private lateinit var getLatestComic : GetLatestComic
    private lateinit var getComicById : GetComicById
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        comicTitle = findViewById(R.id.tv_comic_title)
        comicImage = findViewById(R.id.iv_comic_image)
        nextButton = findViewById(R.id.bt_next_comic)
        prevButton = findViewById(R.id.bt_previous_comic)

        nextButton.setOnClickListener { pressNextButton() }
        prevButton.setOnClickListener { pressPrevButton() }

        val xkcdApiService : XkcdApiService = XkcdApiService.create()
        getLatestComic = GetLatestComic(xkcdApiService)
        getComicById = GetComicById(xkcdApiService)

        mainViewModel = MainViewModel(getLatestComic, getComicById)
        mainViewModel
                .getNotifyObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe {
                    updateUIFromViewModel()
                }

        getLatestComic()
    }

    private fun getLatestComic() {
        mainViewModel.getLatestComic()
    }

    private fun getComicByIndex(index : String) {
        mainViewModel.getComicById(index)
    }

    private fun pressPrevButton() {
        val prevIndex = currentIndex - 1
        getComicByIndex(prevIndex.toString())
    }

    private fun pressNextButton() {
        val prevIndex = currentIndex + 1
        getComicByIndex(prevIndex.toString())
    }

    private fun updateUIFromViewModel() {
        comicTitle.text = mainViewModel.xkcdData?.safe_title
        Picasso.with(this)
                .load(mainViewModel.xkcdData?.img).fit().centerInside()
                .into(comicImage)

        currentIndex = mainViewModel.xkcdData?.num ?: 0
        nextButton.isEnabled = currentIndex < mainViewModel.latestIndex
        prevButton.isEnabled = currentIndex >= 1
    }

}
