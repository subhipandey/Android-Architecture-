

package com.subhipandey.android.loveletter.fragment

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.gson.Gson
import com.subhipandey.android.loveletter.R
import com.subhipandey.android.loveletter.adapter.LetterPagerAdapter
import com.subhipandey.android.loveletter.extension.urlDecode
import com.subhipandey.android.loveletter.model.Letter
import com.subhipandey.android.loveletter.viewmodel.LettersViewModel
import kotlinx.android.synthetic.main.fragment_presentation.*
import kotlinx.android.synthetic.main.letter_page_description.view.*
import kotlinx.android.synthetic.main.letter_page_ps.view.*
import kotlinx.android.synthetic.main.letter_page_title.view.*
class PresentationFragment : Fragment(R.layout.fragment_presentation) {

  private val lettersViewModel: LettersViewModel by navGraphViewModels(R.id.nav_graph)
  private val args: PresentationFragmentArgs by navArgs()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val letter = Gson().fromJson(
      args.letter.urlDecode(),
      Letter::class.java
    )
    lettersViewModel.saveLetterToInbox(letter)

    viewPager.adapter = context?.let {
      LetterPagerAdapter(
        it, letter
      )
    }

    viewPager.setPageTransformer(false) { page, position ->
      page.ivStar?.apply {
        scaleX = (1 - position)
        scaleY = (1 - position)
        translationX = page.width * position * 0.1f
        alpha = 1 - position
      }

      page.ivFlower?.apply {
        translationY = -position * page.height
        translationX = page.width * position * 0.1f
      }

      arrayOf(page.ivDonut1, page.ivDonut2, page.ivDonut3).forEachIndexed { index, imageView ->
        imageView?.apply {
          val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
          layoutParams.circleAngle = 120 * index + 360 * position
          layoutParams.circleAngle %= 360
          layoutParams.circleRadius = Math.abs(300 * (1 - position)).toInt()
          this.layoutParams = layoutParams
        }
      }

      page.ivHearts?.apply {
        translationX = position * page.width * 2
        scaleX = (1 - position)
        scaleY = (1 - position)
      }
      page.ivCandles?.apply {
        translationX = position * page.width * 0.5f
      }

      page.ivAndroid?.apply {
        alpha = 1 - position
      }
    }
  }
}

