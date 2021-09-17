package com.example.framework.di

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.usecases.CheckIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun checkIn (vehicleRepository: IVehicleRepository) : CheckIn = CheckIn(vehicleRepository)

    @Provides
    fun checkOut (vehicleRepository: IVehicleRepository) : CheckIn = CheckIn(vehicleRepository)

}