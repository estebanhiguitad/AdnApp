package com.example.domain.repository

import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.repositories.VehicleRepository
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.*
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
class VehicleRepositoryTest {

    @Mock
    private lateinit var localSource: ILocalSource

    @InjectMocks
    private lateinit var repository: VehicleRepository

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

    private val vehicles = listOf(car,motorcycle)


    @Test
    fun checkIn_whenVehicleIsCar_OkResult(){
        //Arrange
        runBlocking {
            //Act
            repository.checkIn(car)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).saveVehicle(car)
        }
    }

    @Test
    fun checkIn_whenVehicleIsMotorcycle_OkResult(){
        //Arrange
        runBlocking {
            //Act
            repository.checkIn(motorcycle)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).saveVehicle(motorcycle)
        }
    }


    @Test
    fun checkOut_whenVehicleIsCar_OkResult(){
        //Arrange
        runBlocking {
            //Act
            repository.checkOut(car)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).deleteVehicle(car)
        }
    }

    @Test
    fun checkOut_whenVehicleIsMotorcycle_OkResult(){
        //Arrange
        runBlocking {
            //Act
            repository.checkOut(motorcycle)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).deleteVehicle(motorcycle)
        }
    }


    @Test
    fun checkCapacity_whenTypeIsMotorcycle_okResult(){
        //Arrange
        val type = TYPE_MOTORCYCLE
        val cantVehicles = CAPACITY_MOTORCYLE - 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            //Act
            val result = repository.checkCapacity(type)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
            Assert.assertEquals(18,result)
        }
    }

    @Test
    fun checkCapacity_whenTypeIsCar_okResult(){
        //Arrange
        val type = TYPE_CAR
        val cantVehicles = CAPACITY_CAR - 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            //Act
            val result = repository.checkCapacity(type)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
            Assert.assertEquals(8,result)
        }
    }

    @Test
    fun checkCapacity_whenTypeNotExist_exceptionResult(){
        //Arrange
        val type = "NONE"
        val cantVehicles = CAPACITY_CAR - 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            try {
                //Act
                repository.checkCapacity(type)
            }catch (e: Exception){
                //Assert
                Assert.assertTrue(e is VehicleTypeNotExistException)
            }
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
        }
    }

    @Test
    fun vehicleExist_whenLicensePlateExist_trueResult(){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(true)
            //Act
            val response = repository.vehicleExist(licensePlate)
            //Assert
            Assert.assertTrue(response)
        }
    }

    @Test
    fun vehicleExist_whenLicensePlateNotExist_falseResult(){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(false)
            //Act
            val response = repository.vehicleExist(licensePlate)
            //Assert
            Assert.assertEquals(false,response)
        }
    }


    @Test
    fun getVehicle_whenLicensePlateExist_carResult (){
        //Arrange
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.getVehicle(licensePlate)).thenReturn(car)
            //Act
            val response = repository.getVehicle(licensePlate)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).getVehicle(licensePlate)
            Assert.assertEquals(car,response)
        }
    }

    @Test
    fun getVehicle_whenLicensePlateExist_motorcycleResult (){
        //Arrange
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.getVehicle(licensePlate)).thenReturn(motorcycle)
            //Act
            val response = repository.getVehicle(licensePlate)
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).getVehicle(licensePlate)
            Assert.assertEquals(motorcycle,response)
        }
    }

    @Test
    fun getVehicle_whenLicensePlateNotExist_ExceptionResult (){
        //Arrange
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.getVehicle(licensePlate)).thenReturn(null)
            //Act
            try{
                repository.getVehicle(licensePlate)
            }catch (e : Exception){
                Assert.assertTrue(e is VehicleNotExistException)
            }

            //Assert
            Mockito.verify(localSource,Mockito.times(1)).getVehicle(licensePlate)

        }
    }

    @Test
    fun getVehicles (){
        //Arrange
        runBlocking {
            Mockito.`when`(localSource.getVehicles()).thenReturn(vehicles)
            //Act
            val response = repository.getVehicles()
            //Assert
            Mockito.verify(localSource,Mockito.times(1)).getVehicles()
            Assert.assertEquals(vehicles,response)
        }
    }
}