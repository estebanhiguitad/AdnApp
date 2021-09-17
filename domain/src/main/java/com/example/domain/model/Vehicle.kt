package com.example.domain.model

const val TYPE_CAR = "car"
const val TYPE_MOTORCYCLE = "motorcycle"
open class Vehicle(
    open var id: String,
    open var licencePlate : String,
    open var entryDate : String,
    open var departureDate : String,
    open var type: String
){

}

