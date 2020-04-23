

package com.subhipandey.android.mylittledoggo.presentation.doggos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.subhipandey.android.mylittledoggo.domain.DoggoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Suppress("UNCHECKED_CAST")
object DoggoListViewModelFactory : ViewModelProvider.Factory {

  private lateinit var repository: DoggoRepository

  fun inject(repository: DoggoRepository) {
    DoggoListViewModelFactory.repository = repository
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return DoggoListViewModel(repository) as T
  }
}