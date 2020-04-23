

package com.subhipandey.android.mylittledoggo.presentation.doggos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhipandey.android.mylittledoggo.data.api.ConnectionManager
import com.subhipandey.android.mylittledoggo.domain.Doggo
import com.subhipandey.android.mylittledoggo.domain.DoggoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DoggoListViewModel(
    private val repository: DoggoRepository
) : ViewModel() {

  companion object {
    const val PAGE_SIZE = 10
  }

  val doggoList: LiveData<List<Doggo>>
    get() = _doggoList

  private val _doggoList: MutableLiveData<List<Doggo>> = MutableLiveData()

  init {
    observeCacheUpdates()
  }

  fun getMoreDoggos(numberOfDoggos: Int) {
    updateCacheWithDoggosFromApi(numberOfDoggos)
  }

  private fun updateCacheWithDoggosFromApi(numberOfDoggos: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      val doggos = repository.getApiDoggos(numberOfDoggos)
      repository.updateCachedDoggos(doggos)
    }
  }

  private fun observeCacheUpdates() {
    viewModelScope.launch {
      repository.getCachedDoggos()
          .onEach {
            if (it.isEmpty() && ConnectionManager.isConnected()) {
              getMoreDoggos(PAGE_SIZE)
            }
          }
          .flowOn(Dispatchers.IO)
          .collect { handleDoggos(it) }
    }
  }

  private fun handleDoggos(doggos: List<Doggo>) {
    _doggoList.value = doggos
  }
}