package com.example.framework.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.example.framework.database.ParkingDB
import com.example.framework.database.dao.ICarDao
import com.example.framework.database.dao.IMotorcycleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun databaseProvider(app: Application): ParkingDB = Room.databaseBuilder(
        app,
        ParkingDB::class.java,
        "parking-db"
    ).build()

    @Provides
    fun providesCarDao(dataBase : ParkingDB) : ICarDao = dataBase.carDao()

    @Provides
    fun providesMotorcycleDao(dataBase: ParkingDB) : IMotorcycleDao = dataBase.motorcycleDao()

}