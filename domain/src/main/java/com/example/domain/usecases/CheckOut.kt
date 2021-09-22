package com.example.domain.usecases

import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.Vehicle

class CheckOut(private val vehicleRepository: IVehicleRepository) {
    suspend operator fun invoke(
        vehicle: Vehicle
    ){
        val vehicleExist = vehicleRepository.vehicleExist(vehicle.licensePlate)
        if (vehicleExist) {
            vehicleRepository.checkOut(vehicle)
        }else{
            throw VehicleNotExistException()
        }
    }
}
