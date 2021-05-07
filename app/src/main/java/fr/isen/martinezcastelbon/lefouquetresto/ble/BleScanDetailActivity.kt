package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.bluetooth.*
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import fr.isen.martinezcastelbon.lefouquetresto.R
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityBleScanDetailBinding
import fr.isen.martinezcastelbon.lefouquetresto.model.BLEConnexionState

class BleScanDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBleScanDetailBinding
    var bluetoothGatt: BluetoothGatt? = null
    var status: String = "status:  "

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleScanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice>("ble_device")
        bluetoothGatt = device?.connectGatt(this, true, gattBLE )
        binding.nameBle.text = device?.name ?: "Appareil Inconnu au bataillon"
        binding.statBle.text = "Status: en cours de connexion"
        binding.adessBLE.text = device?.address

        bluetoothGatt?.connect()
        connectToDevice(device)
    }

    private fun connectToDevice(device: BluetoothDevice?){
        bluetoothGatt = device?.connectGatt(
                this,
                false,
                gattBLE)
    }

    private val gattBLE = object : BluetoothGattCallback() {

            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int){
                super.onConnectionStateChange(gatt, status, newState)
                connectionStateChange(newState, gatt)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            runOnUiThread {
                binding.recyclerDetail.adapter = BleScanDetailAdapter(
                        gatt,
                        gatt?.services?.map {
                            BleService(
                                    it.uuid.toString(),
                                    it.characteristics
                            )
                        }?.toMutableList() ?: arrayListOf(), this@BleScanDetailActivity
                )
                binding.recyclerDetail.layoutManager = LinearLayoutManager(this@BleScanDetailActivity)
                }
            }

            override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
                super.onCharacteristicRead(gatt, characteristic, status)
                runOnUiThread {
                    binding.recyclerDetail.adapter?.notifyDataSetChanged()
                }
            }
            override fun onCharacteristicWrite(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            runOnUiThread {
                binding.recyclerDetail.adapter?.notifyDataSetChanged()
                }
            }
            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic
            ) {
            super.onCharacteristicChanged(gatt, characteristic)
            runOnUiThread {
                binding.recyclerDetail.adapter?.notifyDataSetChanged()
                }
            }

        }

    private fun connectionStateChange(newState: Int, gatt: BluetoothGatt?){
        BLEConnexionState.getBLEConnexionFromState(newState)?.let {
            runOnUiThread {
                binding.statBle.text =
                        getString(R.string.ble_device_status, getString(it.text))
            }
            if (it.state == BLEConnexionState.STATE_CONNECTED.state) {
                gatt?.discoverServices()
            }
        }
    }


    companion object {
        private const val STATE_DISCONNECTED = "DECONNECTE"
        private const val STATE_CONNECTED = "CONNEXION"
    }
}