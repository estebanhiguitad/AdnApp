package com.example.domain.usescases

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.*
import com.example.domain.usecases.GetVehicles
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class GetVehiclesTest {

    @Mock
    lateinit var vehicleRepository: IVehicleRepository

    @InjectMocks
    lateinit var getVehicles: GetVehicles

    private val car: Vehicle = Car(
        "1",
        "ABD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )

    private val motorcycle: Vehicle = Motorcycle(
        "2",
        "AGG22",
        Calendar.getInstance(), null,
        "300",
        TYPE_MOTORCYCLE
    )

    private val vehicles = listOf(car,motorcycle)
    
    @Test
    fun invokeTest (){
        runBlocking {
            Mockito.`when`(vehicleRepository.getVehicles()).thenReturn(vehicles)
            val result = getVehicles.invoke()
            Mockito.verify(vehicleRepository,Mockito.times(1)).getVehicles()
            Assert.assertEquals(vehicles,result)
        }
    }

}