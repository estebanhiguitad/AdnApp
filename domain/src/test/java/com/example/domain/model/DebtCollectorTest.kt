package com.example.domain.model

import com.example.domain.model.debtcollector.CarDebtCollector
import com.example.domain.model.debtcollector.DAILY_VALUE_CAR
import com.example.domain.model.debtcollector.DebtCollector
import com.example.domain.model.debtcollector.HOURLY_VALUE_CAR
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class DebtCollectorTest {

    private val debtCollector: DebtCollector = DebtCollector(
        INITIAL_PARKING_TIME)


    @Test
    fun getParkedHours (){
        val entryDate = Calendar.getInstance()
        entryDate.add(Calendar.HOUR,-2)

        val motorcycle: Vehicle = Motorcycle(
            2,
            "AGG22",
            entryDate, Calendar.getInstance(),
            "300",
            TYPE_MOTORCYCLE
        )

        val response = debtCollector.getParkedHours(motorcycle)

        Assert.assertEquals(2,response)

    }

    @Test
    fun getTotalPriceParkedHoursLessThanInitialParkingTime (){
        val hourPrice = HOURLY_VALUE_CAR
        val dayPrice = DAILY_VALUE_CAR
        val parkedHours = 2

        val response = debtCollector.getTotalPrice(hourPrice,dayPrice,parkedHours)

        Assert.assertEquals(2000.0,response,0.0)

    }

    @Test
    fun getTotalPriceParkedHoursLessThanADay (){
        val hourPrice = HOURLY_VALUE_CAR
        val dayPrice = DAILY_VALUE_CAR
        val parkedHours = 10

        val response = debtCollector.getTotalPrice(hourPrice,dayPrice,parkedHours)

        Assert.assertEquals(8000.0,response,0.0)
    }

    @Test
    fun getTotalPriceParkedHoursMoreThanADay (){
        val hourPrice = HOURLY_VALUE_CAR
        val dayPrice = DAILY_VALUE_CAR
        val parkedHours = 26

        val response = debtCollector.getTotalPrice(hourPrice,dayPrice,parkedHours)

        Assert.assertEquals(10000.0,response,0.0)
    }

}