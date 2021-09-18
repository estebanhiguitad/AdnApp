package com.example.framework.di

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.usecases.CheckCapacity
import com.example.domain.usecases.CheckIn
import com.example.domain.usecases.CheckOut
import com.example.domain.usecases.GetVehicles
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
    fun checkOut (vehicleRepository: IVehicleRepository) : CheckOut = CheckOut(vehicleRepository)

    @Provides
    fun getVehicles (vehicleRepository: IVehicleRepository) : GetVehicles = GetVehicles(vehicleRepository)

    @Provides
    fun checkCapacity (vehicleRepository: IVehicleRepository) : CheckCapacity = CheckCapacity(vehicleRepository)

}