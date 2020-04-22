

package com.subhipandey.android.bookmanstreasure.ui.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.subhipandey.android.bookmanstreasure.repository.FavoritesRepository

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

  companion object {
    private const val PAGE_SIZE = 100
  }

  private val favoritesRepository = FavoritesRepository(app)

  private val favoritesDataSourceFactory = favoritesRepository.getFavorites()

  private val pagingConfig = PagedList.Config.Builder()
      .setPageSize(PAGE_SIZE)
      .setPrefetchDistance(PAGE_SIZE)
      .setEnablePlaceholders(true)
      .build()

  val data = LivePagedListBuilder(favoritesDataSourceFactory, pagingConfig)
      .build()

  fun refreshList() {
    data.value?.dataSource?.invalidate()
  }
}
