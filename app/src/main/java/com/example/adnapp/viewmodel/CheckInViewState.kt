package com.example.adnapp.viewmodel


sealed class CheckInViewState {
    object Loading : CheckInViewState()
    data class Success (val response: String) : CheckInViewState()
}