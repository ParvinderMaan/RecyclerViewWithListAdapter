package com.maan.expert

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.maan.expert.databinding.ListItemCategoriesBinding
import com.maan.expert.databinding.ListItemProductBinding
import com.maan.expert.model.CargoBox

import java.lang.RuntimeException

class CargoAdapter: ListAdapter<CargoBox,RecyclerView.ViewHolder>(CargoDiffCallback())  {

    override fun getItemViewType(pos: Int)=when(getItem(pos)){
        is CargoBox.CategoryBox -> R.layout.list_item_categories
        is CargoBox.ProductBox -> R.layout.list_item_product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= when(viewType){
            R.layout.list_item_categories -> {
                val binder = ListItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                 CategoryItemViewHolder(binder)
            }
            R.layout.list_item_product -> {
                val binder = ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                 ProductItemViewHolder(binder)
            }
            else -> throw RuntimeException("Invalid View Type $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when(holder){
            is  CategoryItemViewHolder-> {
                holder.bind(getItem(position) as CargoBox.CategoryBox)
            }
            is ProductItemViewHolder -> {
                holder.bind(getItem(position) as CargoBox.ProductBox)
            }

          else -> throw RuntimeException("Invalid ViewHolder $holder")
         }


    class CategoryItemViewHolder(private var binder: ListItemCategoriesBinding) : RecyclerView.ViewHolder(binder.root) {
        private lateinit var item: CargoBox.CategoryBox
        private val categoryAdapter=CategoryAdapter()
        private var layoutManager:LinearLayoutManager


        init {
            binder.tvSeeAll.setOnClickListener {
                Toast.makeText(binder.root.context, "see all clicked !", Toast.LENGTH_SHORT).show()
            }
            layoutManager=LinearLayoutManager(binder.root.context, LinearLayoutManager.HORIZONTAL,false)
            binder.rvCategory.layoutManager=layoutManager
            binder.rvCategory.adapter=categoryAdapter
        }
        private var scrollListener=object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                item.selectedIndex =firstVisibleItem
            }
        }

        fun bind(item:  CargoBox.CategoryBox) {
            this.item=item
            categoryAdapter.submitList(item.lstOfCategory)

            if( item.selectedIndex !=-1){
                binder.rvCategory.scrollToPosition( item.selectedIndex)
            }
            binder.rvCategory.addOnScrollListener(scrollListener)
        }
    }

    class ProductItemViewHolder(private var binder: ListItemProductBinding) : RecyclerView.ViewHolder(binder.root) {
        private lateinit var item: CargoBox.ProductBox
        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "${item.product.name} at pos = $adapterPosition",
                    Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(item: CargoBox.ProductBox) {
            this.item=item
            binder.ivProductThumbnail.setBackgroundColor(Color.parseColor("#000000"))

            binder.tvProductTitle.text = item.product.name
        }
    }
}

class CargoDiffCallback : DiffUtil.ItemCallback<CargoBox>() {

    override fun areItemsTheSame(oldItem: CargoBox, newItem: CargoBox): Boolean {
        if(oldItem is CargoBox.ProductBox && newItem is CargoBox.ProductBox && oldItem.product.id== newItem.product.id)
            return true
        if(oldItem is CargoBox.CategoryBox && newItem is CargoBox.CategoryBox && oldItem.title== newItem.title)
            return true

        return false
    }

    override fun areContentsTheSame(oldItem: CargoBox, newItem: CargoBox): Boolean {
        if(oldItem is CargoBox.ProductBox && newItem is CargoBox.ProductBox && oldItem== newItem)
            return true
        if(oldItem is CargoBox.CategoryBox && newItem is CargoBox.CategoryBox && oldItem== newItem)
            return true

        return false
    }
}