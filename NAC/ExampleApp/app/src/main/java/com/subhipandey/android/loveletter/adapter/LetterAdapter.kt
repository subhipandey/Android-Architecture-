

package com.subhipandey.android.loveletter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subhipandey.android.loveletter.model.FragmentType
import com.subhipandey.android.loveletter.model.Letter
import com.subhipandey.android.loveletter.viewholder.LetterViewHolder
import kotlinx.android.synthetic.main.view_holder_letter.view.*

class LetterAdapter(private val fragmentType: FragmentType) :
  RecyclerView.Adapter<LetterViewHolder>() {

  private val list: MutableList<Letter> = mutableListOf()

  private var itemClickListener: ((Letter) -> Unit)? = null
  private var itemDeleteListener: ((Letter) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return LetterViewHolder(inflater, parent)
  }

  override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
    val item = list[position]
    holder.itemView.setOnClickListener {
      itemClickListener?.invoke(item)
    }
    holder.itemView.ivDelete.setOnClickListener {
      itemDeleteListener?.invoke(item)
    }
    holder.bind(item, fragmentType)
  }

  fun setItemClickListener(listener: (letter: Letter) -> Unit) {
    itemClickListener = listener
  }

  fun setItemDeleteListener(listener: (letter: Letter) -> Unit) {
    itemDeleteListener = listener
  }

  override fun getItemCount(): Int = list.size
  fun update(listItems: List<Letter>) {
    list.clear()
    list.addAll(listItems)
    notifyDataSetChanged()
  }
}


