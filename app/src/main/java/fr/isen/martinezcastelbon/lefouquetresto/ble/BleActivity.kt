package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.martinezcastelbon.lefouquetresto.R
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityBleBinding


class BleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBleBinding
    private var isScanning =false
    private var bluetoothAdapter: BluetoothAdapter? =null
    private var deviceListAdapter: BleScanAdapter? = null
    private val handler = Handler()

    private var bluetoothLeScanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .build()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter
        initRecyclerDevice()
        startBLEIfPossible()
        isDeviceHasBLESupport()

        binding.blePlayPause.setOnClickListener {
            togglePlaypauseAction()
            isDeviceHasBLESupport()
        }
        binding.bleScanTitle.setOnClickListener {
            togglePlaypauseAction()

        }

    }

    private fun startBLEIfPossible() {
        when {
            !isDeviceHasBLESupport() && bluetoothAdapter == null -> {
                Toast.makeText(
                    this,
                    "Cet appareil n'est pas compatible avec le bluetooth",
                    Toast.LENGTH_SHORT
                ).show()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                //je dois activer le bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)

            }
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            }
            else -> {
                //on peut pas faire du BLE
                Log.d("ScanDevices", "onRequestPermissionsResult not PERMISSION")
                bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            startBLEIfPossible()

        }
    }

    private fun isDeviceHasBLESupport(): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Cet appareil n'est pas compatible, sorry", Toast.LENGTH_SHORT)
                    .show()
        }
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    private fun initRecyclerDevice() {
        deviceListAdapter = BleScanAdapter(mutableListOf())
        binding.listeBle.layoutManager = LinearLayoutManager(this)
        binding.listeBle.adapter = deviceListAdapter
    }


    private fun togglePlaypauseAction(){
        isScanning = !isScanning
        if(isScanning){
            binding.bleScanTitle.text = getString(R.string.ble_title)
            binding.blePlayPause.setImageResource(R.drawable.ic_pause)
            binding.progressBarBle.isVisible = true
            binding.divider.isVisible = false
            scanLeDevice()

        } else{
            binding.bleScanTitle.text = getString(R.string.ble_title2)
            binding.blePlayPause.setImageResource(R.drawable.ic_play)
            binding.progressBarBle.visibility = View.INVISIBLE
            binding.divider.isVisible = true

        }
    }


    private fun scanLeDevice() {
        bluetoothAdapter?.bluetoothLeScanner?.let { scanner ->
            if (isScanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    isScanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                isScanning = true
                scanner.startScan(null, scanSettings, leScanCallback)
            } else {
                isScanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("BLEScan", "CC LE SCAN")
            deviceListAdapter?.addDevice(result)
            deviceListAdapter?.notifyDataSetChanged()

        }
    }
    companion object{
        const private val REQUEST_ENABLE_BT= 33
        const private val REQUEST_PERMISSION_LOCATION = 33
        // Stops scanning after 10 seconds.
        private val SCAN_PERIOD: Long = 10000
    }
}