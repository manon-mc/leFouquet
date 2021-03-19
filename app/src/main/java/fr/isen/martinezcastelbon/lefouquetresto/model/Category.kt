package fr.isen.martinezcastelbon.lefouquetresto.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name_fr") val name: String,
    @SerializedName("items") val dishes: List<Dish> )
