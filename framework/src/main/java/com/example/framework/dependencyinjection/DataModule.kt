package com.example.framework.dependencyinjection

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.data.repositories.VehicleRepository
import com.example.domain.data.sources.ILocalSource
import com.example.framework.database.CarLocalSource
import com.example.framework.database.IVehicleSource
import com.example.framework.database.MotorcycleLocalSource
import com.example.framework.database.ParkingLocalSource
import com.example.framework.database.dao.ICarDao
import com.example.framework.database.dao.IMotorcycleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesRepository (localSource: ILocalSource) : IVehicleRepository = VehicleRepository(localSource)

    @Provides
    fun providesLocalSource (carDao: ICarDao, motorcycleDao: IMotorcycleDao) : ILocalSource = ParkingLocalSource(carDao,motorcycleDao)

}