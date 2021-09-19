package com.example.domain.model

import java.util.*

data class Car(
    override var id: Int?,
    override var licensePlate: String,
    override var entryDate: Calendar,
    override var departureDate: Calendar?,
    override var type: String
) : Vehicle(
    id,
    licensePlate, entryDate,
    departureDate,
    type
) {

}