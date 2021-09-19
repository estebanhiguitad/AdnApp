package com.example.framework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
abstract class VehicleEntity(
    @PrimaryKey(autoGenerate = true) open var id: Int?,
    open var licensePlate: String,
    open var entryDate: String,
    open var departureDate: String?,
    open var type: String

) {
    fun mapStringToDate (date: String) : Calendar {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis =  date.toLong()
        return calendar
    }
}