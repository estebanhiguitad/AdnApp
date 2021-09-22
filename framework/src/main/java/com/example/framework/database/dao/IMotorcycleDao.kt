package com.example.framework.database.dao

import androidx.room.*
import com.example.framework.database.entities.MotorcycleEntity

@Dao
interface IMotorcycleDao {
    @Query ("SELECT * FROM MotorcycleEntity")
    fun getAll(): List<MotorcycleEntity>

    @Query ( "SELECT COUNT(*) FROM MotorcycleEntity")
    fun getCountMotorcycle () : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMotorcycle(motorcycle: MotorcycleEntity)

    @Query ( "DELETE FROM MotorcycleEntity WHERE licensePlate = :licensePlate")
    fun deleteMotorcycle (licensePlate : String)

    @Query("SELECT * FROM MotorcycleEntity WHERE licensePlate = :licensePlate")
    fun getMotorcycle (licensePlate: String): MotorcycleEntity

    @Query( "SELECT COUNT(*) FROM MotorcycleEntity WHERE licensePlate = :licensePlate")
    fun countMotorcycleByLicence(licensePlate: String): Int
}