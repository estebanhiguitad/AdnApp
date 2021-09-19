package com.example.domain.usescases

import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.Car
import com.example.domain.model.TYPE_CAR
import com.example.domain.model.Vehicle
import com.example.domain.usecases.CheckOut
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
class CheckOutTest {
    @Mock
    lateinit var vehicleRepository: IVehicleRepository

    @InjectMocks
    lateinit var checkOut : CheckOut

    private val car: Vehicle = Car(
        "1",
        "ABD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )

    @Test
    fun invokeTest(){
        runBlocking {
            try{
                checkOut.invoke(car)
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }
            Mockito.verify(vehicleRepository, Mockito.times(1)).checkOut(car)
        }
    }
}