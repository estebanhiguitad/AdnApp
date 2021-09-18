package com.example.domain.usecases

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.Vehicle

class GetVehicles (private val vehicleRepository: IVehicleRepository) {
    suspend operator fun invoke() = vehicleRepository.getVehicles()
}