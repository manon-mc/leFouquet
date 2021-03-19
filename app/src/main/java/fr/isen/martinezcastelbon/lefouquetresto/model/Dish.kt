package fr.isen.martinezcastelbon.lefouquetresto.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val title: String,
    @SerializedName("prices") val prices: List<Price>,
    @SerializedName("images") private val images: List<String>,
): Serializable {

    fun getAffichagePrice() = prices[0].price
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()){
        images[0]
    }else {
        null
    }


}

