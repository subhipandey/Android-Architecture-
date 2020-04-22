

package com.subhipandey.android.loveletter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subhipandey.android.loveletter.R
import com.subhipandey.android.loveletter.extension.getString
import com.subhipandey.android.loveletter.helper.DateTimeHelper
import com.subhipandey.android.loveletter.model.FragmentType
import com.subhipandey.android.loveletter.model.Letter
import kotlinx.android.synthetic.main.view_holder_letter.view.*

class LetterViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
  RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_letter, parent, false)) {

  private val dateTimeHelper = DateTimeHelper()

  fun bind(letter: Letter, fragmentType: FragmentType) {
    itemView.tvTitle.text = letter.title
    when (fragmentType) {
      FragmentType.SENT -> {
        itemView.tvEmail.text = itemView.getString(R.string.to_email, letter.to ?: "")
        itemView.tvDate.text = dateTimeHelper.parse(letter.sentAt)
      }
      FragmentType.INBOX -> {
        itemView.tvEmail.text = itemView.getString(R.string.from_email, letter.from ?: "")
        itemView.tvDate.text = dateTimeHelper.parse(letter.receivedAt)
      }
    }
  }
}
