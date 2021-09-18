package com.example.domain.data.repositories

import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.*

class VehicleRepository (private val localSource: ILocalSource) :IVehicleRepository {

    override suspend fun checkIn (vehicle: Vehicle){
        val vehicleExist = localSource.vehicleExist(vehicle.licensePlate)
        if(vehicleExist) {
            throw VehicleAreadyExistException()
        }else{
            localSource.saveVehicle(vehicle)
        }
    }

    override suspend fun checkOut (vehicle: Vehicle){
        val vehicleExist = localSource.vehicleExist(vehicle.licensePlate)
        if (vehicleExist) {
             localSource.deleteVehicle(vehicle)
        }else{
            throw VehicleNotExistException()
        }
    }

    override suspend fun checkCapacity (type: String):Boolean {
        return when (type){
            TYPE_MOTORCYCLE -> (localSource.getNumberVehicles(type)<= CAPACITY_MOTORCYLE)
            TYPE_CAR -> (localSource.getNumberVehicles(type) <= CAPACITY_CAR)
            else -> throw VehicleTypeNotExistException()
        }
    }

    override suspend fun getVehicle (licensePlate: String) = localSource.getVehicle(licensePlate)

    override suspend fun getVehicles () = localSource.getVehicles()

}