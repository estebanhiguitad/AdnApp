package com.example.framework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Motorcycle

@Entity
data class MotorcycleEntity(
    @PrimaryKey override var id: String,
    override var licencePlate: String,
    override var entryDate: String,
    override var departureDate: String,
    val cylinderCapacity: String,
    override var type: String
) : VehicleEntity(
    id, licencePlate, entryDate, departureDate, type
) {

    constructor(motorcycle: Motorcycle) : this(
        id = motorcycle.id,
        licencePlate = motorcycle.licencePlate,
        entryDate = motorcycle.entryDate,
        departureDate = motorcycle.departureDate,
        cylinderCapacity = motorcycle.cylinderCapacity,
        type = motorcycle.type
    )

    fun map(): Motorcycle {
        return Motorcycle(
            id,
            licencePlate,
            entryDate,
            departureDate,
            cylinderCapacity,
            type
        )
    }

}