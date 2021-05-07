package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.martinezcastelbon.lefouquetresto.R
import fr.isen.martinezcastelbon.lefouquetresto.databinding.CellBleBinding

class BleAdapter(private val listdevice: MutableList<ScanResult>,
                 private val clickListener: (ScanResult) -> Unit) :
    RecyclerView.Adapter<BleAdapter.DeviceViewHolder>() {

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
        holder.numero.text = listdevice[position].rssi.toString()

        holder.layout.setOnClickListener {
            clickListener.invoke(listdevice[position])
        }
    }

    fun addDevice(appareilData: ScanResult) {
        //Log.d("BleScanAdapter", "ajout de  : ${appareilData.device.address}")
        if (!listdevice.contains(appareilData))
            listdevice.add(appareilData)
    }

    override fun getItemCount(): Int = listdevice.size

    class DeviceViewHolder(binding: CellBleBinding) : RecyclerView.ViewHolder(binding.root) {
        val titledevice: TextView = binding.titreDevice
        val deviceAddress: TextView = binding.adresseDevice
        val numero: TextView = itemView.findViewById(R.id.buttonNum)
        val layout = itemView.findViewById<View>(R.id.cellDevice)
    }
}

