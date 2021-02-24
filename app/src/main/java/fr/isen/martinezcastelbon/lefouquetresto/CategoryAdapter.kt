package fr.isen.martinezcastelbon.lefouquetresto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.martinezcastelbon.lefouquetresto.databinding.CellCategoryBinding

class CategoryAdapter(private val categories: List<String>, private val clickListener: onItemClickListener) :
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
        holder.title.text = categories[position]
        holder.layout.setOnClickListener{
            clickListener.onItemClicked(categories[position])
        }
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.cellCategoryTitle)
        val layout = view.findViewById<View>(R.id.cellLayout)
        }
    interface onItemClickListener {
        fun onItemClicked(item: String)
    }

    }


