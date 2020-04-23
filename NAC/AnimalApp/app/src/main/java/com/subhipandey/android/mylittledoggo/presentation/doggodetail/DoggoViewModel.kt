

package com.subhipandey.android.mylittledoggo.presentation.doggodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhipandey.android.mylittledoggo.domain.DoggoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoggoViewModel(private val repository: DoggoRepository) : ViewModel() {

  fun updateDoggoFavoriteStatus(doggoPicture: String, isFavorite: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.updateDoggoFavoriteStatus(doggoPicture, isFavorite)
    }
  }
}