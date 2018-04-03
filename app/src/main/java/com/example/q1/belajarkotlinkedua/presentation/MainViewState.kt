package com.example.q1.belajarkotlinkedua.presentation

import com.example.q1.belajarkotlinkedua.domain.XkcdDataResult

data class MainViewState(var data: XkcdDataResult, var isNextButtonEnabled: Boolean, var isPrevButtonEnabled : Boolean)