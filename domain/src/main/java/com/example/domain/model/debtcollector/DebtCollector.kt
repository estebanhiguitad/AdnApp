package com.example.domain.model.debtcollector

import com.example.domain.model.Vehicle



open class DebtCollector ( private val initialParkingTime : Int) {

    fun getParkedHours (vehicle: Vehicle) :Int{
        return (((vehicle.departureDate?.timeInMillis ?: 0) - vehicle.entryDate.timeInMillis) /3600000).toInt()
    }

    fun getTotalPrice (hourPrice: Double, dayPrice: Double, parkerHours: Int): Double{
        var totalPrice = 0.0
        when {
            parkerHours < initialParkingTime -> totalPrice =  parkerHours * hourPrice
            parkerHours in initialParkingTime..24 -> totalPrice = dayPrice
            parkerHours > 24 -> {
                val days = parkerHours / 24
                val hours = parkerHours % 24
                totalPrice = if ( hours >= initialParkingTime) (days+1) * dayPrice
                else (days*dayPrice) + (hours*hourPrice)
            }
        }
        return totalPrice
    }

}