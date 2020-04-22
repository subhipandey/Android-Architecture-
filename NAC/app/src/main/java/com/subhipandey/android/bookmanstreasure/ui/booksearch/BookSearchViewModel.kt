

package com.subhipandey.android.bookmanstreasure.ui.booksearch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.subhipandey.android.bookmanstreasure.api.OpenLibraryApi
import com.subhipandey.android.bookmanstreasure.data.SearchCriteria
import com.subhipandey.android.bookmanstreasure.source.NetworkState
import com.subhipandey.android.bookmanstreasure.source.WorkDataSourceFactory

class BookSearchViewModel : ViewModel() {
  companion object {
    private const val PAGE_SIZE = 100
  }

  private val workDataSourceFactory = WorkDataSourceFactory(
      OpenLibraryApi.create()
  )

  private val pagingConfig = PagedList.Config.Builder()
      .setPageSize(PAGE_SIZE)
      .setPrefetchDistance(PAGE_SIZE)
      .setEnablePlaceholders(true)
      .build()

  val data = LivePagedListBuilder(workDataSourceFactory, pagingConfig)
      .build()

  val networkState: LiveData<NetworkState> = Transformations.switchMap(workDataSourceFactory.sourceLiveData) {
    it.networkState
  }

  fun updateSearchTerm(searchTerm: String) {
    workDataSourceFactory.searchTerm.postValue(searchTerm)
    workDataSourceFactory.sourceLiveData.value?.invalidate()
  }

  fun updateSearchCriteria(searchCriteria: SearchCriteria) {
    workDataSourceFactory.searchCriteria.postValue(searchCriteria)
    workDataSourceFactory.sourceLiveData.value?.invalidate()
  }
}
