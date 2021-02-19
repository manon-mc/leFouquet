package fr.isen.martinezcastelbon.lefouquetresto


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.starter.setOnClickListener {
             // bouton entrees
            //Toast.makeText(this, "Entrées", Toast.LENGTH_SHORT)
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category", "Entrées")
            startActivity(intent)
        }
        binding.plats.setOnClickListener {
            // bouton plats
           // Toast.makeText(this, "Plats", Toast.LENGTH_SHORT)
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category", "Plats")
            startActivity(intent)
        }
        binding.desserts.setOnClickListener {
            // bouton desserts
            //Toast.makeText(this, "Desserts", Toast.LENGTH_SHORT)
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category", "Desserts")
            startActivity(intent)
        }


    }
}

