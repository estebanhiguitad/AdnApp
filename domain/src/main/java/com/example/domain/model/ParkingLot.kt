package com.example.domain.model

import com.example.domain.data.exceptions.EntryDeniedException
import com.example.domain.data.exceptions.VehicleClassNotExistException
import com.example.domain.model.debtcollector.CarDebtCollector
import com.example.domain.model.debtcollector.MotorcycleDebtCollector
import java.util.*

const val CAPACITY_MOTORCYLE: Int = 20
const val CAPACITY_CAR: Int = 10
class ParkingLot (private  val carDebtCollector: CarDebtCollector, private val motorcycleDebtCollector: MotorcycleDebtCollector) {
    private val initialParkingTime : Int = 9
    private val forbiddenExceptMondayAndSunday : Char = 'A'

    fun getTotalPrice(vehicle: Vehicle): Double {
        return when (vehicle) {
            is Car -> carDebtCollector.getTotal(vehicle)
            is Motorcycle -> motorcycleDebtCollector.getTotal(vehicle)
            else -> throw VehicleClassNotExistException()
        }
    }

    fun validateCheckIn (vehicle: Vehicle) {
        val dayOfWeek = Calendar.DAY_OF_WEEK
        if (vehicle.licensePlate.lowercase().startsWith(forbiddenExceptMondayAndSunday.lowercaseChar()) && dayOfWeek in 1..2){
            throw EntryDeniedException()
        }
    }
}