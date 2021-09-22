package com.example.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.framework.database.entities.CarEntity

@Dao
interface ICarDao {
    @Query("SELECT * FROM CarEntity")
    fun getAll(): List<CarEntity>

    @Query( "SELECT COUNT(*) FROM CarEntity")
    fun getCountCar () : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCar(car: CarEntity)

    @Query( "DELETE FROM CarEntity WHERE licensePlate = :licensePlate")
    fun deleteCar (licensePlate : String)

    @Query("SELECT * FROM CarEntity WHERE licensePlate = :licensePlate")
    fun getCar (licensePlate: String) : CarEntity

    @Query( "SELECT COUNT(*) FROM CarEntity WHERE licensePlate = :licensePlate")
    fun countCarByLicence(licensePlate: String): Int
}