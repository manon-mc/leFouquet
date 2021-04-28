package fr.isen.martinezcastelbon.lefouquetresto.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("id") val id: String,
    @SerializedName("name_fr") val title: String,
    @SerializedName("images")  val images: List<String>,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("prices") val prices: List<Price>
): Serializable {

    fun getAffichagePrice() = prices[0].price
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()){
        images[0]
    }else {
        null
    }


}

