package com.example.domain.data.sources

import com.example.domain.model.Vehicle

interface ILocalSource {
    suspend fun vehicleExist(licensePlate : String) : Boolean
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
    suspend fun getVehicle(licensePlate : String) : Vehicle
    suspend fun getVehicles() : List<Vehicle>
    suspend fun getNumberVehicles (type:String): Int
}