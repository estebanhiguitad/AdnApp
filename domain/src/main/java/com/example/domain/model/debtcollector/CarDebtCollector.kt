package com.example.domain.model.debtcollector

import com.example.domain.model.Vehicle

const val HOURLY_VALUE_CAR: Double = 1000.0
const val DAILY_VALUE_CAR: Double = 8000.0
class CarDebtCollector (initialParkingTime : Int) :DebtCollector(initialParkingTime)  ,IDebtCollector {

    override fun getTotal(vehicle: Vehicle):Double {
        return getTotalPrice(HOURLY_VALUE_CAR, DAILY_VALUE_CAR, getParkedHours(vehicle))
    }



}