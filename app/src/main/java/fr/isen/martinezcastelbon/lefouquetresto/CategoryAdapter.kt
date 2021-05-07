package fr.isen.martinezcastelbon.lefouquetresto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.martinezcastelbon.lefouquetresto.databinding.CellCategoryBinding
import fr.isen.martinezcastelbon.lefouquetresto.model.Dish

class CategoryAdapter(private val categories: List<Dish>, private val clickListener: onItemClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {

       val binding = CellCategoryBinding
           .inflate(LayoutInflater.from(parent.context),parent, false)

        return CategoryViewHolder(binding.root)
    }

    override fun getItemCount(): Int = categories.size


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val dish = categories[position]
        holder.layout.setOnClickListener{
            clickListener.onItemClicked(dish)
        }
        holder.bind(dish)
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.cellCategoryTitle)
        val layout = view.findViewById<View>(R.id.cellLayout)
        val images: ImageView = view.findViewById(R.id.imageView2)
        val prices: TextView = view.findViewById(R.id.categoryPrice)

            fun bind(dish: Dish){
                title.text = categories[position].title
                prices.text = "${dish.prices.first().price}"
                Picasso.get()
                   .load(dish.getFirstPicture())
                    .into(images)
            }
        }
    interface onItemClickListener {
        fun onItemClicked(item: Dish)
    }

    }


