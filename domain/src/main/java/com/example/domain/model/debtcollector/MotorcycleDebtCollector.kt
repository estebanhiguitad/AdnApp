package com.example.domain.model.debtcollector

import com.example.domain.model.Motorcycle
import com.example.domain.model.Vehicle

const val HOURLY_VALUE_MOTORCYLE: Double = 500.0
const val DAILY_VALUE_MOTORCYCLE: Double = 4000.0
const val MINIMUM_CYLINDER_CAPACITY : Int = 500
const val EXTRA_VALUE_PER_CYLINDER : Int = 2000
class MotorcycleDebtCollector (initialParkingTime : Int) :DebtCollector(initialParkingTime) {
    override fun getTotal(vehicle: Vehicle): Double {
        var totalPrice = getTotalPrice(HOURLY_VALUE_MOTORCYLE, DAILY_VALUE_MOTORCYCLE,getParkedHours(vehicle))
        if(vehicle is Motorcycle && vehicle.cylinderCapacity.toInt() > MINIMUM_CYLINDER_CAPACITY) totalPrice += EXTRA_VALUE_PER_CYLINDER.toDouble()
        return totalPrice
    }
}