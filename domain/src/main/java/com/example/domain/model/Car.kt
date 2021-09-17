package com.example.domain.model

data class Car(
    override var id: String,
    override var licencePlate: String,
    override var entryDate: String,
    override var departureDate: String,
    override var type: String
) : Vehicle(
    id,
    licencePlate, entryDate,
    departureDate,
    type
) {

}