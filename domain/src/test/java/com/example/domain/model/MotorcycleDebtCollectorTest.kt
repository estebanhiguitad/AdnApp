package com.example.domain.model

import com.example.domain.model.debtcollector.MotorcycleDebtCollector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import java.lang.AssertionError
import java.time.temporal.ChronoUnit
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class MotorcycleDebtCollectorTest {

    private val motorcycleDebtCollector: MotorcycleDebtCollector = MotorcycleDebtCollector(
        INITIAL_PARKING_TIME)

    @Test
    fun getTotal(){

        val entryDate = Calendar.getInstance()
        entryDate.add(Calendar.HOUR,-2)

        val motorcycle: Vehicle = Motorcycle(
            2,
            "AGG22",
            entryDate, Calendar.getInstance(),
            "300",
            TYPE_MOTORCYCLE
        )

        val response = motorcycleDebtCollector.getTotal(motorcycle)

        Assert.assertEquals(1000.0,response,0.0)

    }

}