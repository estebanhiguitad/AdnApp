package com.example.domain.model

data class Motorcycle(
    override var id: String,
    override var licencePlate: String,
    override var entryDate: String,
    override var departureDate: String,
    var cylinderCapacity: String,
    override var type: String
) : Vehicle(
    id,
    licencePlate, entryDate,
    departureDate, type
) {

}