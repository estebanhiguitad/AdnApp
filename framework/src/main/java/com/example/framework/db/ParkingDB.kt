package com.example.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.framework.db.dao.ICarDao
import com.example.framework.db.dao.IMotorcycleDao
import com.example.framework.db.entities.CarEntity
import com.example.framework.db.entities.MotorcycleEntity

@Database(entities = [CarEntity::class, MotorcycleEntity::class], version = 1, exportSchema = false)
abstract class ParkingDB : RoomDatabase() {
    abstract fun carDao() : ICarDao
    abstract fun motorcycleDao() : IMotorcycleDao
}