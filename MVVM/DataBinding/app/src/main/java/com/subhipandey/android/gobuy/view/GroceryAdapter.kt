

package com.subhipandey.android.gobuy.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.gobuy.databinding.GroceryListItemBinding
import com.subhipandey.android.gobuy.R
import com.subhipandey.android.gobuy.databinding.GroceryListItemBinding
import com.subhipandey.android.gobuy.model.GroceryItem

class GroceryAdapter(val items: ArrayList<GroceryItem>, val context: Context,
                     val itemEditListener: (position: Int) -> Unit,
                     val itemDeleteListener: (position: Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    // Gets the number of groceries in the list
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: GroceryListItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.grocery_list_item,
                parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val description = item.amount.toString() + "x: " + item.itemName
        holder.bind(items[position])
        holder.binding.buttonEdit.setOnClickListener { itemEditListener(position) }
        holder.binding.buttonDelete.setOnClickListener { itemDeleteListener(position) }
    }
}

class ViewHolder(val binding: GroceryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GroceryItem) {
        binding.apply {
            itemName = "${item.amount}x: ${item.itemName}"
            price = item.price.toString()
        }
    }
}