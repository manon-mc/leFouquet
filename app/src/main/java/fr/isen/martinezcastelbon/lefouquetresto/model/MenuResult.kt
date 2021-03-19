package fr.isen.martinezcastelbon.lefouquetresto.model

import com.google.gson.annotations.SerializedName

data class MenuResult(@SerializedName("data") val categories: List<Category>)