package com.example.domain.data.repositories

import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.*

class VehicleRepository (private val localSource: ILocalSource) :IVehicleRepository {

    override suspend fun checkIn (vehicle: Vehicle){
        localSource.saveVehicle(vehicle)
    }

    override suspend fun checkOut (vehicle: Vehicle){
        localSource.deleteVehicle(vehicle)
    }

    override suspend fun checkCapacity (type: String):Int {
        return localSource.getNumberVehicles(type)
    }

    override suspend fun vehicleExist(licensePlate: String): Boolean {
        return localSource.vehicleExist(licensePlate)
    }

    override suspend fun getVehicle (licensePlate: String) = localSource.getVehicle(licensePlate)

    override suspend fun getVehicles () = localSource.getVehicles()

}