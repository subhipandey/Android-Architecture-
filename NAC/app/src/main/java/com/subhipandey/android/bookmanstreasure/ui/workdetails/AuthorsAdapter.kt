

package com.subhipandey.android.bookmanstreasure.ui.workdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.subhipandey.android.bookmanstreasure.R
import com.subhipandey.android.bookmanstreasure.data.Author

class AuthorsAdapter(
    private val authors: List<Author>,
    var itemCLickListener: ((Author) -> Unit)? = null
) : RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val author: TextView = view.findViewById(R.id.tvAuthor)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_author, parent, false)
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = authors[position]

    holder.author.text = item.name

    holder.itemView.setOnClickListener { itemCLickListener?.invoke(item) }
  }

  override fun getItemCount() = authors.size

}