package fr.isen.martinezcastelbon.lefouquetresto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.martinezcastelbon.lefouquetresto.databinding.ActivityCategoryBinding
import fr.isen.martinezcastelbon.lefouquetresto.model.Dish
import fr.isen.martinezcastelbon.lefouquetresto.model.MenuResult
import org.json.JSONException
import org.json.JSONObject


class CategoryActivity : AppCompatActivity(), CategoryAdapter.onItemClickListener{
    private var requestQueue: RequestQueue? = null

    private lateinit var binding: ActivityCategoryBinding

    fun RecupPlats(categoryTitle: String) {
        val body = JSONObject();
        body.put("id_shop", "1")
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val request = JsonObjectRequest(Request.Method.POST, url, body, Response.Listener {
                response ->try {
            Log.d("de", response.toString())
            val menu = Gson().fromJson(response.toString(), MenuResult::class.java)
            displayMenu(menu, categoryTitle)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener {
                error -> error.printStackTrace()
        })
        requestQueue?.add(request)
    }

    private fun displayMenu(menu: MenuResult, categoryTitle: String){
        val dishesByCategory = menu.categories.firstOrNull{ it.name == categoryTitle }?.dishes ?: listOf()
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = CategoryAdapter(dishesByCategory,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
        val categoryName = intent.getStringExtra("category")?:""

        binding.categoryTitle.text = categoryName
        RecupPlats(categoryName)
    }

    override fun onItemClicked(item: Dish) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("dish", item)
        startActivity(intent)
    }


}