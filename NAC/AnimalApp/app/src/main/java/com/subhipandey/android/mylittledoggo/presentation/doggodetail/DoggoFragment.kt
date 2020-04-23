

package com.subhipandey.android.mylittledoggo.presentation.doggodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.subhipandey.android.mylittledoggo.R
import com.subhipandey.android.mylittledoggo.presentation.extensions.load
import kotlinx.android.synthetic.main.fragment_doggo.*

class DoggoFragment : Fragment() {

  private val args: DoggoFragmentArgs by navArgs()
  private val viewModel: DoggoViewModel by viewModels { DoggoViewModelFactory }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_doggo, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val (picture, isFavorite) = args

    setupFavoriteButton(picture, isFavorite)

    image_view_full_screen_doggo.load(picture)
  }

  private fun setupFavoriteButton(picture: String, pictureIsFavorite: Boolean) {
    updateButtonBackground(pictureIsFavorite)
    button_favorite.isChecked = pictureIsFavorite
    button_favorite.setOnCheckedChangeListener { _, isChecked ->
      viewModel.updateDoggoFavoriteStatus(picture, isChecked)
      updateButtonBackground(isChecked)
    }
  }

  private fun updateButtonBackground(pictureIsFavorite: Boolean) {
    val buttonImageResource: Int = if (pictureIsFavorite) {
      R.drawable.ic_star_full_42dp
    } else {
      R.drawable.ic_star_border_42dp
    }

    button_favorite.background = resources.getDrawable(buttonImageResource, null)
  }
}