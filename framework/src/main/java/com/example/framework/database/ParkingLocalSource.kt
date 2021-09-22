package com.example.framework.database

import com.example.domain.data.exceptions.VehicleClassNotExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.*
import com.example.framework.database.dao.ICarDao
import com.example.framework.database.dao.IMotorcycleDao
import com.example.framework.database.entities.CarEntity
import com.example.framework.database.entities.MotorcycleEntity

class ParkingLocalSource (private val carDao: ICarDao, private val motorcycleDao : IMotorcycleDao): ILocalSource {

    override suspend fun vehicleExist(licensePlate: String): Boolean {
        return carDao.countCarByLicence(licensePlate) > 0 || motorcycleDao.countMotorcycleByLicence(licensePlate) > 0
    }

    override suspend fun saveVehicle(vehicle: Vehicle) {
        getTypeOfVehicle(vehicle.type).saveVehicle(vehicle)
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        getTypeOfVehicle(vehicle.type).deleteVehicle(vehicle)
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
       return getTypeOfVehicle(type).getNumberVehicles(type)
    }

     private fun getTypeOfVehicle (type:String):IVehicleSource{
        return when(type){
            TYPE_MOTORCYCLE -> MotorcycleLocalSource(motorcycleDao)
            TYPE_CAR -> CarLocalSource(carDao)
            else -> throw VehicleTypeNotExistException()
        }
    }


}