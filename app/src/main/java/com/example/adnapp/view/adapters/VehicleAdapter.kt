package com.example.adnapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adnapp.databinding.ItemVehicleBinding
import com.example.adnapp.fragment.CheckInDialogFragment
import com.example.adnapp.fragment.CheckOutDialogFragment
import com.example.domain.model.Vehicle
import java.util.*

class VehicleAdapter constructor( private val onClick:IOnClickItemList) : RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    var vehicles: List<Vehicle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVehicleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val vehicle =  vehicles[position]
        holder.bind(vehicle,onClick)
    }

    override fun getItemCount(): Int = vehicles.size

    class ViewHolder (private val binding: ItemVehicleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(vehicle: Vehicle, onClick: IOnClickItemList) {
            val entryDate = Calendar.getInstance()
            entryDate.timeInMillis = vehicle.entryDate.timeInMillis
            val entryDateFormat: String =
                entryDate.get(Calendar.YEAR).toString() + "-" + entryDate.get(Calendar.MONTH)
                    .toString() + "-" + entryDate.get(Calendar.DAY_OF_MONTH)
                    .toString() + ":" + entryDate.get(Calendar.HOUR_OF_DAY)
                    .toString() + " - " + entryDate.get(Calendar.MINUTE).toString()
            binding.titleVehicle.text = vehicle.type
            //binding.entryDate.text = vehicle.mapDate(vehicle.entryDate)
            binding.entryDate.text = entryDateFormat
            binding.licensePlate.text = vehicle.licensePlate
            binding.checkOut.setOnClickListener(View.OnClickListener {
                vehicle.departureDate = Calendar.getInstance()
                onClick.onClickItem(vehicle)
            })
        }
    }
}