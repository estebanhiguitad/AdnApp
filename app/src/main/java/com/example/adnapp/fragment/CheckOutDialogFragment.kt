package com.example.adnapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.adnapp.R
import com.example.adnapp.databinding.FragmentCheckOutDialogBinding
import com.example.adnapp.viewmodel.CheckInViewModel
import com.example.adnapp.viewmodel.CheckOutViewModel
import androidx.lifecycle.Observer
import com.example.adnapp.viewmodel.CheckInViewState
import com.example.adnapp.viewmodel.CheckOutViewState
import com.example.domain.data.exceptions.InvalidDataException
import com.example.domain.model.Vehicle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class CheckOutDialogFragment (private val callback: ICallBackCheckInFragment, private val vehicle: Vehicle) : BottomSheetDialogFragment() {

    private  var _binding: FragmentCheckOutDialogBinding? = null

    private val viewModel: CheckOutViewModel by activityViewModels()

    private val binding get() = _binding!!


    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckOutDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDataVehicle(vehicle)
        binding.checkOut.setOnClickListener(View.OnClickListener {
            viewModel.checkOut().observe(requireActivity(), Observer(::updateUi))
            viewModel.checkOutResponse(vehicle)
        })
    }

    private fun showDataVehicle (vehicle: Vehicle){
        val totalPrice = viewModel.getTotalPrice(vehicle)
        binding.licensePlate.text = vehicle.licensePlate
        binding.amount.text = totalPrice.toInt().toString()
    }

    private fun updateUi(checkOutViewState: CheckOutViewState) {

        when (checkOutViewState) {
            is CheckOutViewState.Success -> {
                val response = checkOutViewState.response
                callback.refreshView(response)
                dismiss()
            }
            is CheckOutViewState.Loading -> Log.d("CHECK", "Loading...")

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}