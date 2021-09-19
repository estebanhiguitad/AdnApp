package com.example.adnapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.exceptions.VehicleAreadyExistException
import com.example.domain.model.Vehicle
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
                checkIn.invoke(vehicle)
            }catch (e: Exception){
                println(e.message)
                response = when (e){
                    is VehicleAreadyExistException -> "Vehicle already exist"
                    else -> "Ups, an error occurred, please try later "
                }
            }
            model.postValue(CheckInViewState.Success(response))
        }
    }

}