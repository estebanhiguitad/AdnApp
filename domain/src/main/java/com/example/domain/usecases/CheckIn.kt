package com.example.domain.usecases

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.Vehicle

class CheckIn (private val vehicleRepository: IVehicleRepository) {
    suspend operator fun invoke (
        vehicle: Vehicle
    ) = vehicleRepository.checkIn(vehicle)

}