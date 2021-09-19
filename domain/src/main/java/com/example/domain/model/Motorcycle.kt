package com.example.domain.model

import java.util.*

data class Motorcycle(
    override var id: Int?,
    override var licensePlate: String,
    override var entryDate: Calendar,
    override var departureDate: Calendar?,
    var cylinderCapacity: String,
    override var type: String
) : Vehicle(
    id,
    licensePlate, entryDate,
    departureDate, type
) {

}