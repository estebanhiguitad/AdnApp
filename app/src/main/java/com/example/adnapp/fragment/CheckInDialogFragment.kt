package com.example.adnapp.fragment

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.adnapp.databinding.FragmentCheckInDialogListDialogBinding
import com.example.adnapp.viewmodel.CheckInViewModel
import com.example.adnapp.viewmodel.CheckInViewState
import com.example.adnapp.viewmodel.VehiclesViewState
import com.example.domain.data.exceptions.InvalidDataException
import com.example.domain.data.exceptions.VehicleTypeNotExistException
import com.example.domain.model.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.*

@AndroidEntryPoint
class CheckInDialogFragment(private val callback: ICallBackCheckInFragment) : BottomSheetDialogFragment() {

    private  var _binding: FragmentCheckInDialogListDialogBinding? = null

    private val viewModel: CheckInViewModel by activityViewModels()

    private val binding get() = _binding!!

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCheckInDialogListDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkIn.setOnClickListener(View.OnClickListener {
            try {
                viewModel.checkIn().observe(requireActivity(), Observer(::updateUi))
                viewModel.checkInResponse(getVehicleFromView())
            }catch (e : Exception){
                when (e){
                    is InvalidDataException -> callback.refreshView("Please check all form")
                    else -> callback.refreshView("Ups, and error was occurred")
                }
                dismiss()
            }
        })
        binding.typeCar.setOnClickListener(View.OnClickListener {
            binding.layoutCylinder.visibility = View.GONE
        })
        binding.typeMotorcycle.setOnClickListener(View.OnClickListener {
            binding.layoutCylinder.visibility = View.VISIBLE
        })
    }

    private fun updateUi(checkInViewState: CheckInViewState) {

        when (checkInViewState) {
            is CheckInViewState.Success -> {
                val response = checkInViewState.response
                callback.refreshView(response)
                dismiss()
            }
            is CheckInViewState.Loading -> Log.d("CHECK", "Loading...")

        }

    }

    private fun getVehicleFromView (): Vehicle{
        val licensePlate = binding.licensePlate.text.toString()
        val type = if(binding.typeCar.isChecked) TYPE_CAR
        else TYPE_MOTORCYCLE
        val entryDate = Calendar.getInstance()
        val cylinderCapacity = if(type == TYPE_MOTORCYCLE) binding.cylinderCapacity.text.toString()
        else "NONE"
        val vehicle: Vehicle
        if(validateData(licensePlate,type,cylinderCapacity)){
            vehicle = if (type == TYPE_CAR){
                Car(null,licensePlate,entryDate,null,type)
            }else (if( type == TYPE_MOTORCYCLE){
                Motorcycle(null,licensePlate, entryDate,null,cylinderCapacity,type)
            }else{
                throw VehicleTypeNotExistException()
            }) as Vehicle
        }else{
            throw InvalidDataException ()
        }
        return vehicle
    }

    private fun validateData(licensePlate: String, type:String, cylinderCapacity: String): Boolean{
        return (licensePlate.isNotEmpty() && type.isNotEmpty() && ((type == TYPE_MOTORCYCLE && cylinderCapacity.isNotEmpty()) || (type == TYPE_CAR)))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}