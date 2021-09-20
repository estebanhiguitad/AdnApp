package com.example.adnapp.viewmodel

sealed class CheckOutViewState {
    object Loading : CheckOutViewState()
    data class Success (val response: String) : CheckOutViewState()
}