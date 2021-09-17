package com.example.framework.db

import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.Car
import com.example.domain.model.Motorcycle
import com.example.domain.model.Vehicle
import com.example.framework.db.dao.ICarDao
import com.example.framework.db.dao.IMotorcycleDao
import com.example.framework.db.entities.CarEntity
import com.example.framework.db.entities.MotorcycleEntity

class ParkingLocalSource (private val carDao: ICarDao, private val motorcycleDao : IMotorcycleDao): ILocalSource {

    override suspend fun vehicleExist(licencePlate: String): Boolean {
        return carDao.countCarByLicence(licencePlate) > 0 || motorcycleDao.countMotorcycleByLicence(licencePlate) > 0
    }

    override suspend fun saveVehicle(vehicle: Vehicle) {
       when(vehicle){
           is Motorcycle -> motorcycleDao.insertMotorcycle(MotorcycleEntity(vehicle))
           is Car -> carDao.insertCar(CarEntity(vehicle))
       }
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        when(vehicle){
            is Motorcycle -> motorcycleDao.deleteMotorcycle(vehicle.licencePlate)
            is Car -> carDao.deleteCar(vehicle.licencePlate)
        }
    }

    override suspend fun getVehicle(licecePlate: String): Vehicle {
        return when {
            carDao.countCarByLicence(licecePlate)>0 -> carDao.getCar(licecePlate).map()
            motorcycleDao.countMotorcycleByLicence(licecePlate)>0 -> motorcycleDao.getMotorcycle(licecePlate).map()
            else -> throw VehicleNotExistException()
        }
    }

    override suspend fun getVehicles(): List<Vehicle> {
        val vehicles : ArrayList<Vehicle> = ArrayList()
        vehicles.addAll(carDao.getAll().map { it.map() })
        vehicles.addAll(motorcycleDao.getAll().map { it.map() })
        return vehicles
    }

}