package com.example.adnapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetVehicles
import com.example.framework.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesViewModel @Inject constructor(
    private val getVehicles: GetVehicles,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val model : MutableLiveData<VehiclesViewState> = MutableLiveData()

    fun getVehicles (): LiveData<VehiclesViewState>{
        return model
    }

    fun listVehicles (){
        viewModelScope.launch(ioDispatcher) {
            model.postValue(VehiclesViewState.Loading)
            var list = getVehicles.invoke()
            model.postValue(VehiclesViewState.Success(list))
        }
    }

}