package com.example.framework.database

import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.Car
import com.example.domain.model.Vehicle
import com.example.framework.database.dao.ICarDao
import com.example.framework.database.entities.CarEntity

class CarLocalSource(private val carDao: ICarDao): IVehicleSource {

    override suspend fun saveVehicle(vehicle: Vehicle) {
        carDao.insertCar(CarEntity(vehicle as Car))
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        carDao.deleteCar(vehicle.licensePlate)
    }

    override suspend fun getVehicle(licensePlate: String): Vehicle {
        return carDao.getCar(licensePlate).map()
    }

    override suspend fun getNumberVehicles(type: String): Int {
        return carDao.getCountCar()
    }

}