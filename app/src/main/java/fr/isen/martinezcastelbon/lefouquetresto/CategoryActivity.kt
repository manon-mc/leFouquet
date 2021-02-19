package fr.isen.martinezcastelbon.lefouquetresto


import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.MonthDisplayHelper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity(){ //CategoryAdapter.onItemClickListener


    private lateinit var  binding : ActivityCategoryBinding

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
        ))

        //override fun onItemClick(item: Items, position: Int){
         //   val intent = Intent(this, DetailActivity::class)
         //   intent.putExtra("Choco", item.stringResourceId)
         //   intent.putExtra("description", item.description)
         //   intent.putExtra("image", item.image)
         //   startActivity(intent)
        }

    //}

}