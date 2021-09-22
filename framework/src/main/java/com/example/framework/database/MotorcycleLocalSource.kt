package com.example.framework.database

import com.example.domain.model.Motorcycle
import com.example.domain.model.Vehicle
import com.example.framework.database.dao.IMotorcycleDao
import com.example.framework.database.entities.MotorcycleEntity

class MotorcycleLocalSource(private val motorcycleDao: IMotorcycleDao) : IVehicleSource {
    override suspend fun saveVehicle(vehicle: Vehicle) {
        motorcycleDao.insertMotorcycle(MotorcycleEntity(vehicle as Motorcycle))
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        motorcycleDao.deleteMotorcycle(vehicle.licensePlate)
    }

    override suspend fun getVehicle(licensePlate: String): Vehicle {
        return motorcycleDao.getMotorcycle(licensePlate).map()
    }

    override suspend fun getNumberVehicles(type: String): Int {
        return motorcycleDao.getCountMotorcycle()
    }
}