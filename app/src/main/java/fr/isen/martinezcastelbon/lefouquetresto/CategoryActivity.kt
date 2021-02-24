package fr.isen.martinezcastelbon.lefouquetresto


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity(), CategoryAdapter.onItemClickListener{


    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("category")

        binding.categoryTitle.text = categoryName
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = CategoryAdapter(listOf(
            "pate bolo",
            "chips",
            "coquillettes",
            "cacauhete",
            "tomates"
        ),this)
    }

    override fun onItemClicked(item: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("dish",item)
        startActivity(intent)
    }
}