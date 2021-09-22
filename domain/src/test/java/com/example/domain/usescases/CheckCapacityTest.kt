package com.example.domain.usescases

import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.repositories.IVehicleRepository
import com.example.domain.model.CAPACITY_CAR
import com.example.domain.model.CAPACITY_MOTORCYLE
import com.example.domain.model.TYPE_CAR
import com.example.domain.model.TYPE_MOTORCYCLE
import com.example.domain.usecases.CheckCapacity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CheckCapacityTest {

    @Mock
    lateinit var vehicleRepository: IVehicleRepository

    @InjectMocks
    lateinit var  checkCapacity: CheckCapacity

    @Test
    fun checkCapacity_whenTypeIsMotorcycleAndNotExceed_trueResponse(){
        //Arrange
        val type = TYPE_MOTORCYCLE
        val cantVehicles = CAPACITY_MOTORCYLE - 2
        runBlocking {
            Mockito.`when`(vehicleRepository.checkCapacity(type)).thenReturn(cantVehicles)
            //Act
            val result = checkCapacity(type)
            //Assert
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkCapacity(type)
            Assert.assertEquals(true,result)
        }
    }

    @Test
    fun checkCapacity_whenTypeIsMotorcycleAndExceed_falseResponse(){
        //Arrange
        val type = TYPE_MOTORCYCLE
        val cantVehicles = CAPACITY_MOTORCYLE + 2
        runBlocking {
            Mockito.`when`(vehicleRepository.checkCapacity(type)).thenReturn(cantVehicles)
            //Act
            val result = checkCapacity(type)
            //Assert
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkCapacity(type)
            Assert.assertEquals(false,result)
        }
    }

    @Test
    fun checkCapacity_whenTypeIsCarAndNotExceed_trueResponse(){
        //Arrange
        val type = TYPE_CAR
        val cantVehicles = CAPACITY_CAR - 2
        runBlocking {
            Mockito.`when`(vehicleRepository.checkCapacity(type)).thenReturn(cantVehicles)
            //Act
            val result = checkCapacity(type)
            //Assert
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkCapacity(type)
            Assert.assertEquals(true,result)
        }
    }

    @Test
    fun checkCapacity_whenTypeIsCarAndExceed_falseResponse(){
        //Arrange
        val type = TYPE_CAR
        val cantVehicles = CAPACITY_CAR + 2
        runBlocking {
            Mockito.`when`(vehicleRepository.checkCapacity(type)).thenReturn(cantVehicles)
            //Act
            val result = checkCapacity(type)
            //Assert
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkCapacity(type)
            Assert.assertEquals(false,result)
        }
    }

    @Test
    fun checkCapacity_whenTypeNotExist_exceptionResponse(){
        //Arrange
        val type = "NOEXIST"
        runBlocking {
            Mockito.`when`(vehicleRepository.checkCapacity(type)).thenReturn(1)
            try {
                //Act
                checkCapacity(type)
            }catch (e: Exception){
                //Assert
                Assert.assertTrue(e is VehicleTypeNotExistException)
            }
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkCapacity(type)
        }
    }

}