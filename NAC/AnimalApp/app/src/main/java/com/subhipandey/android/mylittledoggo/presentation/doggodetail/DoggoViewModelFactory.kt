

package com.subhipandey.android.mylittledoggo.presentation.doggodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.subhipandey.android.mylittledoggo.domain.DoggoRepository

@Suppress("UNCHECKED_CAST")
object DoggoViewModelFactory : ViewModelProvider.Factory {

  private lateinit var repository: DoggoRepository

  fun inject(repository: DoggoRepository) {
    DoggoViewModelFactory.repository = repository
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return DoggoViewModel(repository) as T
  }
}