package com.example.domain.model.debtcollector

import com.example.domain.model.Vehicle



abstract class DebtCollector ( private val initialParkingTime : Int) {
    private val hoursPerDay = 24
    private val millisPerDay = 3600000

    fun getParkedHours (vehicle: Vehicle) :Int{
        return (((vehicle.departureDate?.timeInMillis ?: 0) - vehicle.entryDate.timeInMillis) /millisPerDay).toInt()
    }

    fun getTotalPrice (hourPrice: Double, dayPrice: Double, parkerHours: Int): Double{
        var totalPrice = 0.0
        when {
            parkerHours < initialParkingTime -> totalPrice =  parkerHours * hourPrice
            parkerHours in initialParkingTime..hoursPerDay -> totalPrice = dayPrice
            parkerHours > hoursPerDay -> {
                val days = parkerHours / hoursPerDay
                val hours = parkerHours % hoursPerDay
                totalPrice = if ( hours >= initialParkingTime) (days+1) * dayPrice
                else (days*dayPrice) + (hours*hourPrice)
            }
        }
        return totalPrice
    }

    abstract fun getTotal(vehicle: Vehicle):Double

}