

package com.subhipandey.android.creaturemon.view.avatars

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.subhipandey.android.creaturemon.R
import com.subhipandey.android.creaturemon.app.inflate
import com.subhipandey.android.creaturemon.model.Avatar

class AvatarAdapter(private val avatars: List<Avatar>, private val listener: AvatarListener)
  : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_avatar))
  }

  override fun getItemCount() = avatars.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(avatars[position])
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var avatar: Avatar

    private val imageView = itemView.findViewById<ImageView>(R.id.avatar)

    init {
      itemView.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(avatar: Avatar) {
      this.avatar = avatar
      val bitmap = BitmapFactory.decodeResource(imageView.context.resources, avatar.drawable)
      imageView.setImageDrawable(BitmapDrawable(imageView.context.resources, bitmap))
    }

    override fun onClick(view: View) {
      listener.avatarClicked(this.avatar)
    }
  }

  interface AvatarListener {
    fun avatarClicked(avatar: Avatar)
  }
}