package com.example.framework.database

import com.example.domain.model.Vehicle

interface IVehicleSource {
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
    suspend fun getVehicle(licensePlate : String) : Vehicle
    suspend fun getNumberVehicles (type:String): Int
}