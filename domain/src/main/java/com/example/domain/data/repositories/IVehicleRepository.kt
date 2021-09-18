package com.example.domain.data.repositories

import com.example.domain.model.Vehicle

interface IVehicleRepository {
    suspend fun checkIn (vehicle: Vehicle)
    suspend fun checkOut (vehicle: Vehicle)
    suspend fun getVehicle (licencePlate: String) : Vehicle
    suspend fun getVehicles () : List<Vehicle>
    suspend fun checkCapacity (type: String):Boolean
}