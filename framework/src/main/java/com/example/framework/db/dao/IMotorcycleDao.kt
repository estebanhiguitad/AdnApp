package com.example.framework.db.dao

import androidx.room.*
import com.example.domain.model.Motorcycle
import com.example.framework.db.entities.MotorcycleEntity

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