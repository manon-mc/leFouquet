package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import fr.isen.martinezcastelbon.lefouquetresto.R
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityBleBinding


class BleActivity : AppCompatActivity() {
    private var isScanning = false
    private lateinit var binding: ActivityBleBinding
    private var bluetoothAdapter: BluetoothAdapter? = null

    private val bluetoothLeScanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler()

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter
        startBleIfPossible()

        binding.bleScanTitle.setOnClickListener {
            togglePlaypauseAction()
        }
        binding.blePlayPause.setOnClickListener {
            togglePlaypauseAction()
        }
    }
    private fun scanLeDevice() {
        bluetoothLeScanner?.let { scanner ->
            if (!scanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    scanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                scanning = true
                scanner.startScan(leScanCallback)
            } else {
                scanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    private val leDeviceListAdapter = BleScanAdapter()

    
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            //leDeviceListAdapter.addDevice(result.device)
            //leDeviceListAdapter.notifyDataSetChanged()
        }
    }


    private fun startBleIfPossible() {
        when {
            !isDeviceHasBleSupport() || bluetoothAdapter == null -> {
                Toast.makeText(
                    this, "Cet appareil n'est pas compatible avec le bluetooth",
                    Toast.LENGTH_SHORT
                ).show()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                // je dois activer le ble
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent,
                    REQUEST_ENABLE_BT
                )
            }
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ENABLE_BT
                )
                }

            else -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK){
            startBleIfPossible()
        }
    }


    private fun isDeviceHasBleSupport(): Boolean =
        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)


    private fun togglePlaypauseAction(){
        isScanning = !isScanning
        if(isScanning){
            binding.bleScanTitle.text = getString(R.string.ble_title)
            binding.blePlayPause.setImageResource(R.drawable.ic_pause)
            binding.progressBarBle.isVisible = true
            binding.divider.isVisible = false

        } else{
            binding.bleScanTitle.text = getString(R.string.ble_title)
            binding.blePlayPause.setImageResource(R.drawable.ic_play)
            binding.progressBarBle.visibility = View.INVISIBLE
            binding.divider.isVisible = true

        }
    }

    companion object {
        const private  val REQUEST_ENABLE_BT = 33
        const private val REQUEST_PERMISSION_LOCAL = 22
    }
}