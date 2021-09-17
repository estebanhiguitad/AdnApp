package com.example.framework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Car

@Entity
data class CarEntity(
    @PrimaryKey override var id: String,
    override var licencePlate: String,
    override var entryDate: String,
    override var departureDate: String,
    override var type: String
) :
    VehicleEntity(id, licencePlate, entryDate, departureDate, type) {


    constructor(car: Car) : this(
        id = car.id,
        licencePlate = car.licencePlate,
        entryDate = car.entryDate,
        departureDate = car.departureDate,
        type = car.type
    )

    fun map(): Car {
        return Car(
            id,
            licencePlate,
            entryDate,
            departureDate,
            type
        )
    }
}