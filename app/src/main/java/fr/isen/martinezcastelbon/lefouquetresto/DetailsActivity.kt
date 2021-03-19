package fr.isen.martinezcastelbon.lefouquetresto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.martinezcastelbon.lefouquetresto.model.Dish


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val dish = intent.getSerializableExtra("dish") as? Dish
        val dishName = dish?.title

        Toast.makeText(this, dishName?:"", Toast.LENGTH_LONG).show()
    }

}