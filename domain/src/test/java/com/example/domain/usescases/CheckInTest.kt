package com.example.domain.usescases

import com.example.domain.data.exceptions.VehicleAreadyExistException
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
import java.util.*

@RunWith(MockitoJUnitRunner::class)
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

    private val motorcycle: Vehicle = Motorcycle(
        2,
        "AGG22",
        Calendar.getInstance(), null,
        "300",
        TYPE_MOTORCYCLE
    )


    @Test
    fun checkIn_whenVehicleCarExist_exceptionResult (){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(true)
            try {
                //Act
                checkIn.invoke(car)
            }catch (e: Exception){

                //Assert
                Assert.assertTrue(e is VehicleAreadyExistException)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(0)).checkIn(car)

        }
    }

    @Test
    fun checkIn_whenVehicleMotorcycleExist_exceptionResult (){
        //Arrange
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(true)
            try {
                //Act
                checkIn.invoke(motorcycle)
            }catch (e: Exception){
                //Assert
                Assert.assertTrue(e is VehicleAreadyExistException)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(0)).checkIn(motorcycle)

        }
    }

    @Test
    fun checkIn_whenVehicleCarNotExist_okResult(){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(false)
            try {
                //Act
                checkIn.invoke(car)
                //Assert
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkIn(car)

        }
    }

    @Test
    fun checkIn_whenVehicleMotorcycleNotExist_okResult(){
        //Arrange
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(vehicleRepository.vehicleExist(licensePlate)).thenReturn(false)
            try {
                //Act
                checkIn.invoke(motorcycle)
                //Assert
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(vehicleRepository,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkIn(motorcycle)

        }
    }

}