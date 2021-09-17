package com.example.domain.data.repositories

import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.Vehicle

class VehicleRepository (private val localSource: ILocalSource) :IVehicleRepository {

    override suspend fun checkIn (vehicle: Vehicle){
        val vehicleExist = localSource.vehicleExist(vehicle.licencePlate)
        if(vehicleExist) {
            throw VehicleAreadyExistException()
        }else{
            localSource.saveVehicle(vehicle)
        }
    }

    override suspend fun checkOut (vehicle: Vehicle){
        val vehicleExist = localSource.vehicleExist(vehicle.licencePlate)
        if (vehicleExist) {
             localSource.deleteVehicle(vehicle)
        }else{
            throw VehicleNotExistException()
        }
    }

    override suspend fun getVehicle (licencePlate: String) = localSource.getVehicle(licencePlate)

}