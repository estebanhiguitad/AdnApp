package com.example.domain.repository

import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.data.repositories.VehicleRepository
import com.example.domain.data.sources.ILocalSource
import com.example.domain.model.*
import com.ibm.icu.impl.Assert.fail
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
    fun checkInWhenVehicleCarExist (){
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(true)
            try {
                repository.checkIn(car)
            }catch (e: Exception){
                Assert.assertTrue(e is VehicleAreadyExistException)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(0)).saveVehicle(car)

        }
    }

    @Test
    fun checkInWhenVehicleMotorcycleExist (){
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(true)
            try {
                repository.checkIn(motorcycle)
            }catch (e: Exception){
                Assert.assertTrue(e is VehicleAreadyExistException)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(0)).saveVehicle(motorcycle)

        }
    }

    @Test
    fun checkInWhenVehicleCarNotExist(){
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(false)
            try {
                repository.checkIn(car)
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(1)).saveVehicle(car)

        }
    }

    @Test
    fun checkInWhenVehicleMotorcycleNotExist(){
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(false)
            try {
                repository.checkIn(motorcycle)
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(1)).saveVehicle(motorcycle)

        }
    }

    @Test
    fun checkOutWhenVehicleCarExist(){
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(true)
            try {
                repository.checkOut(car)
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(1)).deleteVehicle(car)

        }
    }

    @Test
    fun checkOutWhenVehicleMotorcycleExist(){
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(true)
            try {
                repository.checkOut(motorcycle)
                Assert.assertTrue(true)
            }catch (e: Exception){
                Assert.assertTrue(false)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(1)).deleteVehicle(motorcycle)

        }
    }

    @Test
    fun checkOutWhenVehicleCarNotExist(){
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(false)
            try {
                repository.checkOut(car)
            }catch (e: Exception){
                Assert.assertTrue(e is VehicleNotExistException)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(0)).deleteVehicle(car)

        }
    }

    @Test
    fun checkOutWhenVehicleMotorcycleNotExist(){
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.vehicleExist(licensePlate)).thenReturn(false)
            try {
                repository.checkOut(motorcycle)
            }catch (e: Exception){
                Assert.assertTrue(e is VehicleNotExistException)
            }

            Mockito.verify(localSource,Mockito.times(1)).vehicleExist(licensePlate)
            Mockito.verify(localSource,Mockito.times(0)).deleteVehicle(motorcycle)

        }
    }

    @Test
    fun checkCapacityWhenTypeIsMotorcycleAndNotExceed(){
        val type = TYPE_MOTORCYCLE
        val cantVehicles = CAPACITY_MOTORCYLE - 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            val result = repository.checkCapacity(type)
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
            Assert.assertEquals(true,result)
        }
    }

    @Test
    fun checkCapacityWhenTypeIsMotorcycleAndExceed(){
        val type = TYPE_MOTORCYCLE
        val cantVehicles = CAPACITY_MOTORCYLE + 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            val result = repository.checkCapacity(type)
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
            Assert.assertEquals(false,result)
        }
    }

    @Test
    fun checkCapacityWhenTypeIsCarAndNotExceed(){
        val type = TYPE_CAR
        val cantVehicles = CAPACITY_CAR - 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            val result = repository.checkCapacity(type)
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
            Assert.assertEquals(true,result)
        }
    }

    @Test
    fun checkCapacityWhenTypeIsCarAndExceed(){
        val type = TYPE_CAR
        val cantVehicles = CAPACITY_CAR + 2
        runBlocking {
            Mockito.`when`(localSource.getNumberVehicles(type)).thenReturn(cantVehicles)
            val result = repository.checkCapacity(type)
            Mockito.verify(localSource,Mockito.times(1)).getNumberVehicles(type)
            Assert.assertEquals(false,result)
        }
    }

    @Test
    fun checkCapacityWhenTypeNotExist(){
        val type = "NOEXIST"
        runBlocking {
            try {
                repository.checkCapacity(type)
            }catch (e: Exception){
                Assert.assertTrue(e is VehicleTypeNotExistException)
            }
            Mockito.verify(localSource,Mockito.times(0)).getNumberVehicles(type)
        }
    }

    @Test
    fun getVehicles (){
        runBlocking {
            Mockito.`when`(localSource.getVehicles()).thenReturn(vehicles)
            val response = repository.getVehicles()
            Mockito.verify(localSource,Mockito.times(1)).getVehicles()
            Assert.assertEquals(vehicles,response)
        }
    }

    @Test
    fun getVehicleCar (){
        val licensePlate = "ABD123"
        runBlocking {
            Mockito.`when`(localSource.getVehicle(licensePlate)).thenReturn(car)
            val response = repository.getVehicle(licensePlate)
            Mockito.verify(localSource,Mockito.times(1)).getVehicle(licensePlate)
            Assert.assertEquals(car,response)
        }
    }

    @Test
    fun getVehicleMotorcycle (){
        val licensePlate = "AGG22"
        runBlocking {
            Mockito.`when`(localSource.getVehicle(licensePlate)).thenReturn(motorcycle)
            val response = repository.getVehicle(licensePlate)
            Mockito.verify(localSource,Mockito.times(1)).getVehicle(licensePlate)
            Assert.assertEquals(motorcycle,response)
        }
    }

}