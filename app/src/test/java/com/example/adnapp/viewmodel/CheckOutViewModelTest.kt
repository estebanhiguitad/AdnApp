package com.example.adnapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.adnapp.CoroutineTestRule
import com.example.domain.model.*
import com.example.domain.usecases.CheckOut
import junit.framework.Assert.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import java.lang.Exception
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class CheckOutViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var checkOut: CheckOut

    @Mock
    lateinit var observer: Observer<CheckOutViewState>

    private lateinit var checkOutViewModel: CheckOutViewModel

    @Before
    fun start() {
        checkOutViewModel = CheckOutViewModel(checkOut, coroutineTestRule.testDispatcher)
    }

    private val car: Vehicle = Car(
        1,
        "ABD123",
        Calendar.getInstance(), null,
        TYPE_CAR
    )


    @Test
    fun checkOut() {
        val response = "Vehicle was delete correctly"
        runBlocking {
            checkOutViewModel.checkOut().observeForever(observer)
            checkOutViewModel.checkOutResponse(car)
            Mockito.verify(checkOut, Mockito.times(1)).invoke(car)
            Mockito.verify(observer, Mockito.times(1)).onChanged(CheckOutViewState.Loading)
            Mockito.verify(observer, Mockito.times(1)).onChanged(CheckOutViewState.Success(response))
            checkOutViewModel.checkOut().removeObserver(observer)
        }
    }
}