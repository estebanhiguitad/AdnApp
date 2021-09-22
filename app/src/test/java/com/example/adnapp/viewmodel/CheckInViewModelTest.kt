package com.example.adnapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.adnapp.CoroutineTestRule
import androidx.lifecycle.Observer
import com.example.domain.model.*
import com.example.domain.model.debtcollector.CarDebtCollector
import com.example.domain.model.debtcollector.MotorcycleDebtCollector
import com.example.domain.usecases.CheckCapacity
import com.example.domain.usecases.CheckIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import java.lang.Exception
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class CheckInViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
     lateinit var checkIn: CheckIn

    @Mock
    lateinit var checkCapacity: CheckCapacity

    @Mock
    lateinit var observer: Observer<CheckInViewState>

    private lateinit var checkInViewModel: CheckInViewModel

    @Before
    fun start() {
        checkInViewModel = CheckInViewModel(checkIn,checkCapacity, coroutineTestRule.testDispatcher)
    }

    private val car: Vehicle = Car(
        1,
        "PBD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )


    @Test
    fun checkIn() {
        val response = "Vehicle was registered correctly"
        runBlocking {
            checkInViewModel.checkIn().observeForever(observer)
            Mockito.`when`(checkCapacity.invoke(car.type)).thenReturn(true)
            checkInViewModel.checkInResponse(car)
            Mockito.verify(checkCapacity, Mockito.times(1)).invoke(car.type)
            Mockito.verify(checkIn, Mockito.times(1)).invoke(car)
            Mockito.verify(observer, Mockito.times(1)).onChanged(CheckInViewState.Loading)
            Mockito.verify(observer, Mockito.times(1)).onChanged(CheckInViewState.Success(response))
            checkInViewModel.checkIn().removeObserver(observer)
        }
    }


}