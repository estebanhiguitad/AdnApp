package com.example.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.framework.database.dao.ICarDao
import com.example.framework.database.dao.IMotorcycleDao
import com.example.framework.database.entities.CarEntity
import com.example.framework.database.entities.MotorcycleEntity

@Database(entities = [CarEntity::class, MotorcycleEntity::class], version = 1, exportSchema = false)
abstract class ParkingDB : RoomDatabase() {
    abstract fun carDao() : ICarDao
    abstract fun motorcycleDao() : IMotorcycleDao
}