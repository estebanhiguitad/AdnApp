package com.example.framework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
abstract class VehicleEntity(
    @PrimaryKey open var id: String,
    open var licencePlate: String,
    open var entryDate: String,
    open var departureDate: String,
    open var type: String

) {

}