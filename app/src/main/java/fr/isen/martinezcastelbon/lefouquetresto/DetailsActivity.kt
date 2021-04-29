package fr.isen.martinezcastelbon.lefouquetresto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


import fr.isen.martinezcastelbon.lefouquetresto.model.Dish
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val dataItem = intent.getSerializableExtra("items") as? Dish
        setContentView(binding.root)
        var sampleImages = arrayOf(dataItem?.images?.get(0))
        val carouselView = findViewById<CarouselView>(R.id.carouselImageView);

        var imageListener: ImageListener = object : ImageListener {
            override fun setImageForPosition(position: Int, imageView: ImageView) {
                if (dataItem != null) {
                    Picasso.get().load(dataItem.images[position]).into(imageView)
                }
            }
        }
        carouselView.setPageCount(sampleImages.size);
        carouselView.setImageListener(imageListener);

        if (dataItem != null) {
            binding.nomPlats.text = dataItem.title
            binding.titlePrice.text = dataItem.getAffichagePrice()
            binding.detailsDesPlats.text = dataItem.getIngredients()
            binding.totalPrice.text = dataItem.getAffichagePrice()
            binding.carouselImageView.pageCount = dataItem.images.size
        }
        binding.carouselImageView.setImageListener(imageListener)

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
