package com.example.adnapp.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.adnapp.databinding.FragmentVehiclesBinding
import com.example.adnapp.view.adapters.IOnClickItemList
import com.example.adnapp.view.adapters.VehicleAdapter
import com.example.adnapp.viewmodel.VehiclesViewModel
import com.example.adnapp.viewmodel.VehiclesViewState
import com.example.domain.model.Vehicle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class VehiclesFragment : Fragment(), ICallBackCheckInFragment, IOnClickItemList {

    private lateinit var binding: FragmentVehiclesBinding
    private lateinit var adapter: VehicleAdapter

    private val viewModel: VehiclesViewModel by activityViewModels()

    private val callbackModal: ICallBackCheckInFragment = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehiclesBinding.inflate(inflater,container,false)
        val view = binding.root
        adapter = VehicleAdapter(this)
        binding.recyclerVehicles.adapter = adapter
        viewModel.getVehicles().observe(requireActivity(), Observer(::updateUi))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener(View.OnClickListener {
            val modalBottomSheet = CheckInDialogFragment(callbackModal)
            modalBottomSheet.show(childFragmentManager,CheckInDialogFragment.TAG)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.listVehicles()
    }

    private fun updateUi(vehiclesViewState: VehiclesViewState) {
        when (vehiclesViewState) {
            is VehiclesViewState.Success -> {
                adapter.vehicles = vehiclesViewState.vehicles
                adapter.notifyDataSetChanged()
                if (adapter.vehicles.isEmpty()) {
                    view?.let {
                        Snackbar.make(it, "No vehicles in database", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                binding.progress.visibility = View.GONE
            }
            VehiclesViewState.Loading -> binding.progress.visibility = View.VISIBLE
        }
    }

    override fun refreshView(response:String) {
        view?.let {
            Snackbar.make(it, response, Snackbar.LENGTH_SHORT)
                .show()
        }
        onResume()
    }

    override fun onClickItem(vehicle: Vehicle) {
        val modalBottomSheet = CheckOutDialogFragment(callbackModal,vehicle)
        modalBottomSheet.show(childFragmentManager,CheckOutDialogFragment.TAG)
    }

}