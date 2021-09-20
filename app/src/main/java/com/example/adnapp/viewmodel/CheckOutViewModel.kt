package com.example.adnapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.exceptions.EntryDeniedException
import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.data.exceptions.VehicleClassNotExistException
import com.example.domain.data.exceptions.VehicleNotExistException
import com.example.domain.model.INITIAL_PARKING_TIME
import com.example.domain.model.ParkingLot
import com.example.domain.model.Vehicle
import com.example.domain.model.debtcollector.CarDebtCollector
import com.example.domain.model.debtcollector.MotorcycleDebtCollector
import com.example.domain.usecases.CheckOut
import com.example.framework.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel  @Inject constructor(
    private val checkOut: CheckOut,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val model : MutableLiveData<CheckOutViewState> = MutableLiveData()

    fun checkOut (): LiveData<CheckOutViewState> {
        return model
    }

    fun checkOutResponse (vehicle: Vehicle){
        viewModelScope.launch(ioDispatcher) {
            model.postValue(CheckOutViewState.Loading)
            val response = try{
                checkOut.invoke(vehicle)
                "Vehicle was delete correctly"
            }catch (e: Exception){
                when (e){
                    is VehicleNotExistException -> "Vehicle does not exist"
                    is VehicleClassNotExistException -> "Vehicle type does not exist"
                    else -> "Ups, an error occurred, please try later "
                }
            }
            model.postValue(CheckOutViewState.Success(response))
        }
    }

    fun getTotalPrice(vehicle: Vehicle): Double {
        val parkingLot = ParkingLot(
            CarDebtCollector(INITIAL_PARKING_TIME),
            MotorcycleDebtCollector(INITIAL_PARKING_TIME)
        )
        return parkingLot.getTotalPrice(vehicle)
    }

}