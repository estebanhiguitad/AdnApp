package com.example.adnapp.viewmodel

import com.example.domain.model.Vehicle

sealed class VehiclesViewState {
    object Loading : VehiclesViewState()
    data class Success (val vehicles: List<Vehicle>) : VehiclesViewState()
}