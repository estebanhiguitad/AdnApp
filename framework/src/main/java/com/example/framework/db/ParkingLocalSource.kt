package com.example.framework.db

import com.example.domain.data.exceptions.VehicleClassNotExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.*
import com.example.framework.db.dao.ICarDao
import com.example.framework.db.dao.IMotorcycleDao
import com.example.framework.db.entities.CarEntity
import com.example.framework.db.entities.MotorcycleEntity

class ParkingLocalSource (private val carDao: ICarDao, private val motorcycleDao : IMotorcycleDao): ILocalSource {

    override suspend fun vehicleExist(licensePlate: String): Boolean {
        return carDao.countCarByLicence(licensePlate) > 0 || motorcycleDao.countMotorcycleByLicence(licensePlate) > 0
    }

    override suspend fun saveVehicle(vehicle: Vehicle) {
       when(vehicle){
           is Motorcycle -> motorcycleDao.insertMotorcycle(MotorcycleEntity(vehicle))
           is Car -> carDao.insertCar(CarEntity(vehicle))
           else -> throw VehicleClassNotExistException()
       }
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        when(vehicle){
            is Motorcycle -> motorcycleDao.deleteMotorcycle(vehicle.licensePlate)
            is Car -> carDao.deleteCar(vehicle.licensePlate)
            else -> throw VehicleClassNotExistException()
        }
    }

    override suspend fun getVehicle(licensePlate: String): Vehicle {
        return when {
            carDao.countCarByLicence(licensePlate)>0 -> carDao.getCar(licensePlate).map()
            motorcycleDao.countMotorcycleByLicence(licensePlate)>0 -> motorcycleDao.getMotorcycle(licensePlate).map()
            else -> throw VehicleNotExistException()
        }
    }

    override suspend fun getVehicles(): List<Vehicle> {
        val vehicles : ArrayList<Vehicle> = ArrayList()
        vehicles.addAll(carDao.getAll().map { it.map() })
        vehicles.addAll(motorcycleDao.getAll().map { it.map() })
        return vehicles
    }

    override suspend fun getNumberVehicles(type:String): Int {
        return when (type) {
            TYPE_CAR -> carDao.getCountCar()
            TYPE_MOTORCYCLE -> motorcycleDao.getCountMotorcycle()
            else -> throw VehicleTypeNotExistException()
        }
    }


}