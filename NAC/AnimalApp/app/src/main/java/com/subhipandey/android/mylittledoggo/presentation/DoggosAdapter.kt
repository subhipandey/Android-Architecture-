

package com.subhipandey.android.mylittledoggo.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.subhipandey.android.mylittledoggo.R
import com.subhipandey.android.mylittledoggo.domain.Doggo
import com.subhipandey.android.mylittledoggo.presentation.extensions.load
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_view_doggo_item.*

class DoggosAdapter(
    private val onDoggoClickListener: ((view: View, doggo: Doggo) -> Unit)? = null
) : ListAdapter<Doggo, DoggosAdapter.DoggoViewHolder>(ITEM_COMPARATOR) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoViewHolder {
    val inflater = LayoutInflater.from(parent.context)

    return DoggoViewHolder(
        inflater.inflate(
            R.layout.recycler_view_doggo_item,
            parent,
            false
        )
    )
  }

  override fun onBindViewHolder(holder: DoggoViewHolder, position: Int) {
    val item: Doggo = getItem(position)
    holder.bind(item, onDoggoClickListener)
  }

  // Need to implement LayoutContainer so that views are cached correctly
  class DoggoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
      LayoutContainer {

    override val containerView: View?
      get() = itemView

    fun bind(item: Doggo, onDoggoClickListener: ((view: View, image: Doggo) -> Unit)?) {
      with(image_view_doggo) {
        load(item.picture) {
          transitionName = item.picture

          onDoggoClickListener?.let {
            it(this, item)
          }
        }
      }
    }
  }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Doggo>() {
  override fun areItemsTheSame(oldItem: Doggo, newItem: Doggo): Boolean {
    return oldItem.picture == newItem.picture
  }

  override fun areContentsTheSame(oldItem: Doggo, newItem: Doggo): Boolean {
    return oldItem == newItem
  }
}