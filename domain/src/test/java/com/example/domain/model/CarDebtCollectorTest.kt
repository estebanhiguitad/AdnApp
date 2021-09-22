package com.example.domain.model

import com.example.domain.model.debtcollector.CarDebtCollector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CarDebtCollectorTest {

    private val carDebtCollector: CarDebtCollector = CarDebtCollector(
        INITIAL_PARKING_TIME)

    @Test
    fun getTotal(){

        val entryDate = Calendar.getInstance()
        entryDate.add(Calendar.HOUR,-2)

        val car: Vehicle = Car(
            2,
            "AGG22",
            entryDate, Calendar.getInstance(),
            TYPE_CAR
        )

        val response = carDebtCollector.getTotal(car)

        Assert.assertEquals(2000.0,response,0.0)

    }

}