package com.example.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.framework.db.entities.CarEntity

@Dao
interface ICarDao {
    @Query("SELECT * FROM CarEntity")
    fun getAll(): List<CarEntity>

    @Query( "SELECT COUNT(*) FROM CarEntity")
    fun getCountCar () : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCar(car: CarEntity)

    @Query( "DELETE FROM CarEntity WHERE licencePlate = :licencePlate")
    fun deleteCar (licencePlate : String)

    @Query("SELECT * FROM CarEntity WHERE licencePlate = :licencePlate")
    fun getCar (licencePlate: String) : CarEntity

    @Query( "SELECT COUNT(*) FROM CarEntity WHERE licencePlate = :licencePlate")
    fun countCarByLicence(licencePlate: String): Int
}