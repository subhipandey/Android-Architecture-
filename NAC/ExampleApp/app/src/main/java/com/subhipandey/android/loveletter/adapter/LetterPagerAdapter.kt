

package com.subhipandey.android.loveletter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.subhipandey.android.loveletter.R
import com.subhipandey.android.loveletter.extension.getString
import com.subhipandey.android.loveletter.model.Letter
import kotlinx.android.synthetic.main.letter_page_description.view.*
import kotlinx.android.synthetic.main.letter_page_ps.view.*
import kotlinx.android.synthetic.main.letter_page_title.view.*

class LetterPagerAdapter(
  val context: Context,
  val letter: Letter
) : PagerAdapter() {

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val inflater = LayoutInflater.from(context)
    val layout =
      inflater.inflate(LetterPage.values()[position].layoutResId, container, false) as ViewGroup

    when (position) {
      LetterPage.PAGE_TITLE.ordinal -> {
        layout.tvTitle.text = letter.title
        layout.tvFrom.text = container.getString(R.string.from_email, letter.from ?: "")
        layout.tvTo.text = container.getString(R.string.to_email, letter.to ?: "")
      }
      LetterPage.PAGE_DESCRIPTION.ordinal -> layout.tvDescription.text = letter.description
      LetterPage.PAGE_PS.ordinal -> layout.tvPs.text = letter.ps
    }
    container.addView(layout)
    return layout
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View?)
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean {
    return view == `object`
  }

  override fun getCount(): Int {
    return LetterPage.values().size
  }

  private enum class LetterPage(val layoutResId: Int) {
    PAGE_TITLE(R.layout.letter_page_title),
    PAGE_DESCRIPTION(R.layout.letter_page_description),
    PAGE_PS(R.layout.letter_page_ps)
  }

}
