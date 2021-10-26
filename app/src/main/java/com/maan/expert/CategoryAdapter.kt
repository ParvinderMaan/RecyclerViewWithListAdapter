package com.maan.expert

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maan.expert.databinding.ListItemCategoryBinding
import com.maan.expert.model.Category

class CategoryAdapter : ListAdapter<Category, RecyclerView.ViewHolder>(CategoryDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binder = ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is CategoryItemViewHolder)
           holder.bind(getItem(position))
    }


    class CategoryItemViewHolder(private var binder: ListItemCategoryBinding) : RecyclerView.ViewHolder(binder.root) {
        private lateinit var item: Category

        fun bind(item:  Category) {
            this.item=item
            binder.ivCatThumbnail.setBackgroundColor(Color.parseColor("#FF6200EE"))
            binder.tvCatTitle.text = item.name
        }
    }
}



class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        if(oldItem.id== newItem.id) return true
        return false
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        if(oldItem== newItem) return true
        return false
    }
}