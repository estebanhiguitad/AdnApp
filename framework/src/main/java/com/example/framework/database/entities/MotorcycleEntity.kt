package com.example.framework.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Motorcycle

@Entity
data class MotorcycleEntity(
    @PrimaryKey(autoGenerate = true) override var id: Int?,
    override var licensePlate: String,
    override var entryDate: String,
    override var departureDate: String?,
    val cylinderCapacity: String,
    override var type: String
) : VehicleEntity(
    id, licensePlate, entryDate, departureDate, type
) {

    constructor(motorcycle: Motorcycle) : this(
        id = motorcycle.id,
        licensePlate = motorcycle.licensePlate,
        entryDate = motorcycle.mapDate(motorcycle.entryDate),
        departureDate = motorcycle.departureDate?.let { motorcycle.mapDate(it) },
        cylinderCapacity = motorcycle.cylinderCapacity,
        type = motorcycle.type
    )

    fun map(): Motorcycle {
        return Motorcycle(
            id,
            licensePlate,
            mapStringToDate(entryDate),
            departureDate?.let { mapStringToDate(it) },
            cylinderCapacity,
            type
        )
    }

}