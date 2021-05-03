package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.martinezcastelbon.lefouquetresto.databinding.CellBleBinding

class BleScanAdapter (private val listdevice: MutableList<ScanResult>) :
    RecyclerView.Adapter<BleScanAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeviceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellBleBinding.inflate(layoutInflater)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.deviceAddress.text = listdevice[position].device.address
        holder.titledevice.text = listdevice[position].device.name
    }

    fun addDevice(appareilData: ScanResult) {
        Log.d("BleScanAdapter", "ajout de  : ${appareilData.device.address}")
        if (!listdevice.contains(appareilData))
            listdevice.add(appareilData)
    }

    override fun getItemCount(): Int = listdevice.size

    class DeviceViewHolder(binding: CellBleBinding) : RecyclerView.ViewHolder(binding.root) {
        val titledevice: TextView = binding.titreDevice
        val deviceAddress: TextView = binding.adresseDevice
    }
}