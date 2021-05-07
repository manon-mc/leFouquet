package fr.isen.martinezcastelbon.lefouquetresto.ble

import android.app.AlertDialog
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.isen.martinezcastelbon.lefouquetresto.R

class BleScanDetailAdapter(
        private val gatt: BluetoothGatt?,
        private val serviceList: MutableList<BleService>,
        private val context: Context

) :
        ExpandableRecyclerViewAdapter<BleScanDetailAdapter.ServiceViewHolder,
                BleScanDetailAdapter.CharacteristicViewHolder> (serviceList)
    {

        class ServiceViewHolder(itemView: View): GroupViewHolder(itemView){
            val serviceName = itemView.findViewById<TextView>(R.id.accesName)
            val serviceArrow = itemView.findViewById<TextView>(R.id.uuidAcces)

            override fun expand(){
                super.expand()
                serviceArrow.animate().rotation(-180f).setDuration(400L).start()
            }
            override fun collapse(){
                super.collapse()
                serviceArrow.animate().rotation(0f).setDuration(400L).start()
            }

        }
        class CharacteristicViewHolder(itemView: View): ChildViewHolder(itemView) {
            val characName: TextView = itemView.findViewById(R.id.characName)
            val characProp: TextView = itemView.findViewById(R.id.propView)
            val characAcces: TextView = itemView.findViewById(R.id.accesName)
            val characValue: TextView = itemView.findViewById(R.id.valueText)

            val characLire: Button = itemView.findViewById(R.id.lireAction)
            val characEcriture: Button = itemView.findViewById(R.id.ecritureAction)
            val characNotif: Button = itemView.findViewById(R.id.notifAction)

        }
        override  fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int):
                ServiceViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_ble_device_service_cell,
                            parent,
                            false)
            return ServiceViewHolder(view)
        }


        override fun onCreateChildViewHolder(
                parent: ViewGroup,
                viewType: Int
        ) : CharacteristicViewHolder = CharacteristicViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_ble_device_characteristic_cell,
                            parent,
                            false)
        )


        override fun onBindChildViewHolder(
            holder: CharacteristicViewHolder,
            flatPosition: Int,
            group: ExpandableGroup<*>,
            childIndex: Int
        ) {

            holder.characName.text = (group.items[childIndex]
                    as BluetoothGattCharacteristic).uuid.toString()
            val charactAcces: BluetoothGattCharacteristic? = (group as BleService).items[childIndex]
            val uuid = "UUID : ${charactAcces?.uuid}"

            holder.characAcces.text = uuid
            holder.characLire.visibility = View.GONE
            holder.characEcriture.visibility = View.GONE
            holder.characNotif.visibility = View.GONE
            if (charactAcces != null) {
                holder.characProp.text = "proprietes : ${propBle(charactAcces.properties)}"
            }
            holder.characEcriture.setOnClickListener {
                val editView = View.inflate(context, R.layout.activity_write, null)
                val dialogue = AlertDialog.Builder(context)
                dialogue.setView(editView)
                dialogue.setPositiveButton("VALIDER") { _, _ ->
                    //val texte = editView.popup.text.toString().toByteArray()
                    val popup: TextView = editView.findViewById(R.id.editName)
                    val texte = popup.text.toString().toByteArray()
                    if (charactAcces != null) {
                        charactAcces.setValue(texte)
                    }
                    val result1 = gatt?.writeCharacteristic(charactAcces)
                    Log.d("erreur: ", result1.toString())
                    val result = gatt?.readCharacteristic(charactAcces)
                    Log.d("erreur: ", result.toString())
                }
                dialogue.setNegativeButton("ANNULER") {
                    dialogue, _ -> dialogue.cancel() }
                dialogue.create()
                dialogue.show()
            }

            if (charactAcces != null) {
                if (propBle(charactAcces.properties).contains("Lire")) {
                    holder.characLire.visibility = View.VISIBLE
                }
            }
            if (charactAcces != null) {
                if (propBle(charactAcces.properties).contains("Ecrire")) {
                    holder.characEcriture.visibility = View.VISIBLE
                }
            }
            if (charactAcces != null) {
                if (propBle(charactAcces.properties).contains("Notifier")) {
                    holder.characNotif.visibility = View.VISIBLE
                }
            }

            holder.characLire.setOnClickListener {
                val result = gatt?.readCharacteristic(charactAcces)

                Log.d("erreur : ", result.toString())
                if (charactAcces != null) {
                    Log.d("valeur de uid : ", charactAcces.uuid.toString())
                }
                if (charactAcces != null) {
                    if (charactAcces.value != null) {

                        val texteRecu = charactAcces?.let { it1 -> String(it1.value) }
                        holder.characValue.text = "Valeur : ${ texteRecu}"
                    }
                }

            }
            var enable : Boolean = false
            if (charactAcces != null) {
                if (charactAcces.uuid.toString() == BleAcces.getBLEAttributeFromUUID(
                                charactAcces.uuid.toString()).acces && enable) {
                    if (charactAcces.value == null)
                        holder.characValue.text = "Valeur : 0"
                    else {
                        if (charactAcces != null) {
                            holder.characValue.text =
                                    "Valeur : ${boardArray(charactAcces.value)}"
                        }
                    }

                }
            }
            var enabled : Boolean = false
            holder.characNotif.setOnClickListener {
                if (!enabled) {
                    enabled = true
                    gatt?.setCharacteristicNotification(charactAcces, true)

                    if (charactAcces != null) {
                        if (charactAcces.descriptors.size > 0) {

                            val descriptors = charactAcces?.descriptors
                            if (descriptors != null) {
                                for (descriptor in descriptors) {

                                    if (charactAcces.properties and
                                            BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                                        descriptor.value =
                                                if (enabled) BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                                    } else if (charactAcces != null) {
                                        if (charactAcces.properties and
                                                BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) {
                                            descriptor.value =
                                                    if (enabled) BluetoothGattDescriptor.ENABLE_INDICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                                        }
                                    }
                                    gatt?.writeDescriptor(descriptor)
                                }
                            }

                        }
                    }
                } else {
                    enabled = false
                    gatt?.setCharacteristicNotification(charactAcces, false)
                }
            }


        }

        private fun propBle(property: Int): StringBuilder {
            val sb = StringBuilder()

            if (property and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
                sb.append("Ecrire") }
            if (property and BluetoothGattCharacteristic.PROPERTY_READ != 0) {
                sb.append("Lire") }
            if (property and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                sb.append("Notifier") }
            if (sb.isEmpty()) sb.append("Aucun")

            return sb
        }

        private fun boardArray(array: ByteArray): String {
            val but = StringBuilder(array.size*2)
            for ( byte in array ) {
                val toAppend = String.format("%X", byte)
                but.append(toAppend).append("-")
            }
            but.setLength(but.length - 1)
            return but.toString()
        }


        override fun onBindGroupViewHolder(
            holder: ServiceViewHolder,
            flatPosition: Int,
            group: ExpandableGroup<*>
        ) {
            val title = BleAcces.getBLEAttributeFromUUID(group.title).title
            holder.serviceName.text = title
            holder.serviceArrow.text = group.title
        }



        enum class BleAcces(val acces: String, val title: String) {
            ACCES_GENERIQUE("00001800-0000-1000-8000-00805f9b34fb", "Accès générique"),
            ATTRIBUT_GENERIQUE("00001801-0000-1000-8000-00805f9b34fb", "Attribut générique"),
            SERVICE_SPECIFIQUE("466c1234-f593-11e8-8eb2-f2801f1b9fd1", "Service Spécifique "),
            SERVICE_SPE2("466c9abc-f593-11e8-8eb2-f2801f1b9fd1", "Service Spécifique 2"),
            UNKNOW_SERVICE("", "Inconnu");

            companion object {
                fun getBLEAttributeFromUUID(uuid: String) = values().firstOrNull {
                    it.acces == uuid
                } ?: UNKNOW_SERVICE
            }
        }

    }