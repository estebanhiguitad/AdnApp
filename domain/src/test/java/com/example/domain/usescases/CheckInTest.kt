package com.example.domain.usescases

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.*
import com.example.domain.usecases.CheckIn
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
class CheckInTest {
    @Mock
    lateinit var vehicleRepository: IVehicleRepository

    @InjectMocks
    lateinit var checkIn : CheckIn

    private val car: Vehicle = Car(
        1,
        "ABD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )

    @Test
    fun invokeTest(){
        runBlocking {
            try{
                checkIn.invoke(car)
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }
            Mockito.verify(vehicleRepository, Mockito.times(1)).checkIn(car)
        }
    }

}