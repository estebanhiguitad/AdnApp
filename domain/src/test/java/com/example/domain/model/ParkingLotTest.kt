package com.example.domain.model

import com.example.domain.data.exceptions.EntryDeniedException
import com.example.domain.model.debtcollector.CarDebtCollector
import com.example.domain.model.debtcollector.MotorcycleDebtCollector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import java.lang.Exception
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class ParkingLotTest {

    @Mock
    lateinit var carDebtCollector: CarDebtCollector

    @Mock
    lateinit var motorcycleDebtCollector: MotorcycleDebtCollector

    @InjectMocks
    lateinit var parkingLot: ParkingLot

    private val car: Vehicle = Car(
        "1",
        "ABD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )

    private val car2: Vehicle = Car(
        "2",
        "BBD123",
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

    @Test
    fun getTotalPriceCar (){
        val totalTest = 2000.0
        Mockito.`when`(carDebtCollector.getTotal(car)).thenReturn(totalTest)
        val response = parkingLot.getTotalPrice(car)
        Mockito.verify(carDebtCollector,Mockito.times(1)).getTotal(car)
        Mockito.verify(motorcycleDebtCollector,Mockito.times(0)).getTotal(motorcycle)
        Assert.assertEquals(totalTest,response,0.0)
    }

    @Test
    fun getTotalPriceMotorcycle (){
        val totalTest = 2000.0
        Mockito.`when`(motorcycleDebtCollector.getTotal(motorcycle)).thenReturn(totalTest)
        val response = parkingLot.getTotalPrice(motorcycle)
        Mockito.verify(carDebtCollector,Mockito.times(0)).getTotal(car)
        Mockito.verify(motorcycleDebtCollector,Mockito.times(1)).getTotal(motorcycle)
        Assert.assertEquals(totalTest,response,0.0)
    }

    @Test
    fun validateCheckInEntryAccepted (){
        try {
            parkingLot.validateCheckIn(car2)
            Assert.assertTrue(true)
        }catch (e: Exception){
            Assert.assertTrue(false)
        }
    }

    @Test
    fun validateCheckInEntryDenied (){
        try {
            parkingLot.validateCheckIn(car)
        }catch (e: Exception){
            Assert.assertTrue(e is EntryDeniedException)
        }
    }

}