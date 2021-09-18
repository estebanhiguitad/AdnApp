package com.example.framework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Car
import java.util.*

@Entity
data class CarEntity(
    @PrimaryKey override var id: String,
    override var licensePlate: String,
    override var entryDate: String,
    override var departureDate: String?,
    override var type: String
) :
    VehicleEntity(id, licensePlate, entryDate, departureDate, type) {


    constructor(car: Car) : this(
        id = car.id,
        licensePlate = car.licensePlate,
        entryDate = car.mapDate(car.entryDate),
        departureDate = car.departureDate?.let { car.mapDate(it) },
        type = car.type
    )

    fun map(): Car {
        return Car(
            id,
            licensePlate,
            mapStringToDate(entryDate),
            departureDate?.let { mapStringToDate(it) },
            type
        )
    }
}