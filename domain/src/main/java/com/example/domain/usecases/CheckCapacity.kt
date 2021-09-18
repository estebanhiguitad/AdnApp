package com.example.domain.usecases

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.Vehicle

class CheckCapacity (private val vehicleRepository: IVehicleRepository) {
    suspend operator fun invoke (
        type: String
    ) = vehicleRepository.checkCapacity(type)
}