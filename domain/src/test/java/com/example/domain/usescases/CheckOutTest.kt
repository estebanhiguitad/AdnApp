package com.example.domain.usescases

import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.*
import com.example.domain.usecases.CheckOut
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CheckOutTest {
    @Mock
    lateinit var vehicleRepository: IVehicleRepository

    @InjectMocks
    lateinit var checkOut : CheckOut

    private val car: Vehicle = Car(
        1,
        "ABD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )

    private val motorcycle: Vehicle = Motorcycle(
        2,
        "AGG22",
        Calendar.getInstance(), null,
        "300",
        TYPE_MOTORCYCLE
    )


    @Test
    fun checkOut_whenVehicleCarExist_okResponse(){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(true)
            try {
                //Act
                checkOut.invoke(car)
                //Assert
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkOut(car)

        }
    }

    @Test
    fun checkOut_whenVehicleMotorcycleExist_okResponse(){
        //Arrange
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(true)
            try {
                //Act
                checkOut(motorcycle)
                //Assert
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkOut(motorcycle)

        }
    }

    @Test
    fun checkOut_whenVehicleCarNotExist_exceptionResult(){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(false)
            try {
                //Act
                checkOut(car)
            }catch (e: Exception){
                //Assert
                Assert.assertTrue(e is VehicleNotExistException)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(0)).checkOut(car)

        }
    }

    @Test
    fun checkOut_whenVehicleMotorcycleNotExist_exceptionResult(){
        //Arrange
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(false)
            try {
                //Act
                checkOut(motorcycle)
            }catch (e: Exception){
                //Assert
                Assert.assertTrue(e is VehicleNotExistException)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(0)).checkOut(motorcycle)

        }
    }


}