package fr.isen.martinezcastelbon.lefouquetresto.model

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(@SerializedName("name_fr") val title: String): Serializable