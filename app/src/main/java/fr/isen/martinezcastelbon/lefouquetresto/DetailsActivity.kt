package fr.isen.martinezcastelbon.lefouquetresto

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
//import com.synnapps.carouselview.ImageListener
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityDetailsBinding
import fr.isen.martinezcastelbon.lefouquetresto.model.Dish


class DetailsActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityDetailsBinding
    //private lateinit var dish: Dish

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_details)

        //dish = (intent.getSerializableExtra("dish") as? Dish)!!

        //if (dish != null) {
         //   binding.itemTitle.text = dish.title
          //  binding.carousel.pageCount = dish.images.size
          //  binding.carousel.setImageListener(imageListener)
        //}
        //if (dish != null) {
        //    Toast.makeText(this, dish.title ?: "detail", Toast.LENGTH_LONG).show()
        //}

    }
    //val imageListener: ImageListener = object : ImageListener {
    //    override fun setImageForPosition(position: Int, imageView: ImageView?) {
    //        if (dish == null) {
    //            Picasso.get().load(dish.getFirstPicture()).into(imageView)
    //        } else {
    //            Picasso.get().load(dish.images.get(position)).into(imageView)
     //       }
       // }
    //}

}
