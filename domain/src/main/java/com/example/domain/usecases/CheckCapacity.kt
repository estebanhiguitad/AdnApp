package com.example.domain.usecases

import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.*

class CheckCapacity (private val vehicleRepository: IVehicleRepository) {
    suspend operator fun invoke (
        type: String
    ):Boolean{
        val vehicleCount = vehicleRepository.checkCapacity(type)
        return when (type){
            TYPE_MOTORCYCLE -> (vehicleCount <= CAPACITY_MOTORCYLE)
            TYPE_CAR -> (vehicleCount <= CAPACITY_CAR)
            else -> throw VehicleTypeNotExistException()
        }
    }
}