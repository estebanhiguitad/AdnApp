package com.example.adnapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.exceptions.EntryDeniedException
import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.model.INITIAL_PARKING_TIME
import com.example.domain.model.ParkingLot
import com.example.domain.model.Vehicle
import com.example.domain.model.debtcollector.CarDebtCollector
import com.example.domain.model.debtcollector.MotorcycleDebtCollector
import com.example.domain.usecases.CheckCapacity
import com.example.domain.usecases.CheckIn
import com.example.framework.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.Console
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val checkIn: CheckIn,
    private val checkCapacity: CheckCapacity,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val model : MutableLiveData<CheckInViewState> = MutableLiveData()

    fun checkIn (): LiveData<CheckInViewState> {
        return model
    }

    fun checkInResponse (vehicle:Vehicle){
        viewModelScope.launch(ioDispatcher) {
            model.postValue(CheckInViewState.Loading)
            var response = "Vehicle was registered correctly"
            try{
                val parkingLot = ParkingLot(
                    CarDebtCollector(INITIAL_PARKING_TIME),
                    MotorcycleDebtCollector(INITIAL_PARKING_TIME)
                )
                parkingLot.validateCheckIn(vehicle)
                val available = checkCapacity.invoke(vehicle.type)
                if(available) checkIn.invoke(vehicle)
                else response = "Capacity is full"
            }catch (e: Exception){
                response = when (e){
                    is VehicleAreadyExistException -> "Vehicle already exist"
                    is EntryDeniedException -> "Entry denied"
                    else -> "Ups, an error occurred, please try later "
                }
            }
            model.postValue(CheckInViewState.Success(response))
        }
    }

}