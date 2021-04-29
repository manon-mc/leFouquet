package fr.isen.martinezcastelbon.lefouquetresto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


import fr.isen.martinezcastelbon.lefouquetresto.model.Dish
import android.view.LayoutInflater
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val dataItem = intent.getSerializableExtra("items") as? Dish
        setContentView(binding.root)

        if (dataItem != null) {
            binding.nomPlats.text = dataItem.title
            binding.titlePrice.text = dataItem.getAffichagePrice()
            binding.detailsDesPlats.text = dataItem.getIngredients()
            binding.totalPrice.text = dataItem.getAffichagePrice()
            //binding.catagoriesImageDetail.pageCount = dataItem.images.size
        }
        var quantity = 0
        if (dataItem != null) {
            calculTotal(quantity, dataItem)
        }

        // Bouton + plus
        binding.boutonPlus.setOnClickListener {
            quantity++
            binding.quantite.text = quantity.toString()
            if (dataItem != null) {
                calculTotal(quantity, dataItem)
            }
        }

        // Bouton - moins
        binding.boutonMoins.setOnClickListener {
            if (quantity > 0)
                quantity--
            binding.quantite.text = quantity.toString()
            if (dataItem != null) {
                calculTotal(quantity, dataItem)
            }
        }

    }
    private fun calculTotal(quantity: Int, itemPricedata: Dish) {
        val total = quantity * itemPricedata.getPrice()
        "Total : $total €".also {
            binding.totalPrice.text = it
        }
    }

}
