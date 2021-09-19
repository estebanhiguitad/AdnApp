package com.example.domain.model

import java.util.*

const val TYPE_CAR = "car"
const val TYPE_MOTORCYCLE = "motorcycle"
open class Vehicle(
    open var id: Int?,
    open var licensePlate : String,
    open var entryDate : Calendar,
    open var departureDate : Calendar?,
    open var type: String
){
    fun mapDate (date: Calendar): String = date.timeInMillis.toString()
}

