package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityBleScanDetailBinding
import fr.isen.martinezcastelbon.lefouquetresto.model.BLEConnexionState

class BleScanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBleScanDetailBinding
    var bluetoothGatt: BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleScanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice>("ble_device")
        //binding.deviceName.text = device?.name ?: "Appareil inconnu"
        //binding.deviceStatus.text = "Status: en cours de connexion"

        connectToDevice(device)
    }

    private fun connectToDevice(device: BluetoothDevice?){
        bluetoothGatt = device?.connectGatt(this, false, object: BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int){
                super.onConnectionStateChange(gatt, status, newState)


            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
            }

            override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
                super.onCharacteristicRead(gatt, characteristic, status)
            }

        })
    }
    private fun connectionStateChange(newState: Int, gatt: BluetoothGatt?){
        BLEConnexionState.getBLEConnexionFromState(newState)?.let {
            //runOnUiThread { binding.deviceStatus.text = getString(R.string.ble_device_status, getString(it.text)) }

        }
    }
}