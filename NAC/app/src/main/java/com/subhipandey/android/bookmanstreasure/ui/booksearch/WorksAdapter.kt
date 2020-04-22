

package com.subhipandey.android.bookmanstreasure.ui.booksearch

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.subhipandey.android.bookmanstreasure.R
import com.subhipandey.android.bookmanstreasure.data.Work
import com.subhipandey.android.bookmanstreasure.util.CoverSize
import com.subhipandey.android.bookmanstreasure.util.loadCover

class WorksAdapter(
    private val glide: RequestManager,
    var itemClickListener: ((Work) -> Unit)? = null
) : PagedListAdapter<Work, WorksAdapter.ViewHolder>(WORK_COMPARATOR) {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cover: ImageView = view.findViewById(R.id.ivCover)
    val title: TextView = view.findViewById(R.id.tvTitle)
    val author: TextView = view.findViewById(R.id.tvAuthor)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksAdapter.ViewHolder {
    return ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_work, parent, false)
    )
  }

  override fun onBindViewHolder(holder: WorksAdapter.ViewHolder, position: Int) {
    val item = getItem(position)

    if (item?.coverId != null) {
      glide.loadCover(item.coverId, CoverSize.M)
          .error(glide.load(R.drawable.book_cover_missing))
          .into(holder.cover)
    } else {
      glide.load(R.drawable.book_cover_missing)
          .into(holder.cover)
    }

    holder.title.text = getItem(position)?.title
    holder.author.text = getItem(position)?.authorName?.reduce { acc, s -> "$acc, $s" }

    holder.itemView.setOnClickListener {
      if (item != null) itemClickListener?.invoke(item)
    }
  }

  companion object {
    val WORK_COMPARATOR = object : DiffUtil.ItemCallback<Work>() {
      override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean =
          oldItem == newItem

      override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean =
          oldItem.coverId == newItem.coverId
    }
  }
}
