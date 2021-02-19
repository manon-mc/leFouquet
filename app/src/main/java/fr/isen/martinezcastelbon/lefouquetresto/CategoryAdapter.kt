package fr.isen.martinezcastelbon.lefouquetresto

import android.location.GnssAntennaInfo
import android.net.sip.SipAudioCall
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.martinezcastelbon.lefouquetresto.databinding.CellCategoryBinding

private lateinit var binding: CellCategoryBinding

class CategoryAdapter(private val categories: List<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {

       val binding = CellCategoryBinding
           .inflate(LayoutInflater.from(parent.context),parent, false)

        return CategoryViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories[position]
        //holder.title.text = item.titleName
        //holder.textDetails.text = item.description
        //holder.textPrice.text = item.
       // holder.itemView.setOnClickListener { listener.onItemClick(item)}
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.cellCategoryTitle)
        val textPrice: TextView = view.findViewById(R.id.categoryPrice)
        //val textDetails: TextView = view.findViewById(R.id.categoryDetails)

        //init{
         //   itemView.setOnClickListener(this)
        //}
        //override fun onClick(view: View?){
        //    val position: Int = adapterPosition
        //    if( position != RecyclerView.NO_POSITION){
         //       listener.onItemClick(position)
          //  }
        }
    }
    //interface onItemClickListener{
     //   fun onItemClick(categories: List<String>)
     //   abstract fun Intent(categoryActivity): Intent?
    //}
//}

//private fun View.setOnClickListener(categoryViewHolder: CategoryAdapter.CategoryViewHolder) {

//}
