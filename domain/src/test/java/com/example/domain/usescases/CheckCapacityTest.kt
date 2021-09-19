package com.example.domain.usescases

import com.example.domain.data.repositories.IVehicleRepository
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
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class CheckCapacityTest {

    @Mock
    lateinit var vehicleRepository: IVehicleRepository

    @InjectMocks
    lateinit var  checkCapacity: CheckCapacity

    @Test
    fun invokeTest(){
        val type = TYPE_MOTORCYCLE
        runBlocking {
            Mockito.`when`(vehicleRepository.checkCapacity(type)).thenReturn(true)
            val response = checkCapacity.invoke(type)
            Mockito.verify(vehicleRepository,Mockito.times(1)).checkCapacity(type)
            Assert.assertEquals(true,response)
        }
    }

}