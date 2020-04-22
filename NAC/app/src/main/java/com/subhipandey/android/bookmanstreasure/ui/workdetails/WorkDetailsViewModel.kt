

package com.subhipandey.android.bookmanstreasure.ui.workdetails

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import com.subhipandey.android.bookmanstreasure.api.OpenLibraryApi
import com.subhipandey.android.bookmanstreasure.data.Work
import com.subhipandey.android.bookmanstreasure.repository.FavoritesRepository
import com.subhipandey.android.bookmanstreasure.source.BookDataSourceFactory
import com.subhipandey.android.bookmanstreasure.source.NetworkState

class WorkDetailsViewModel(app: Application) : AndroidViewModel(app) {

  companion object {
    private const val WORK_ARGUMENT = "work"

    fun createArguments(work: Work): Bundle {
      val bundle = Bundle()
      bundle.putSerializable(WORK_ARGUMENT, work)

      return bundle
    }
  }

  private val favoritesRepository = FavoritesRepository(app)

  private val bookDataSourceFactory = BookDataSourceFactory(
      OpenLibraryApi.create()
  )

  private val pagingConfig = PagedList.Config.Builder()
      .setPageSize(10)
      .setPrefetchDistance(10)
      .setEnablePlaceholders(true)
      .build()

  val data = LivePagedListBuilder(bookDataSourceFactory, pagingConfig)
      .build()

  val networkState: LiveData<NetworkState> =
      Transformations.switchMap(bookDataSourceFactory.sourceLiveData) {
        it.networkState
      }

  val work: LiveData<Work> = Transformations.map(bookDataSourceFactory.work) { it }

  var favorite: LiveData<Work> = MutableLiveData()

  fun loadArguments(arguments: Bundle?) {
    if (arguments == null) {
      return
    }

    val work: Work? = arguments.get(WORK_ARGUMENT) as Work?

    if (work != null) {
      favorite = favoritesRepository.getFavorite(work.id)

      bookDataSourceFactory.work.postValue(work)
      bookDataSourceFactory.sourceLiveData.value?.invalidate()
    }
  }

  fun addAsFavorite(): Boolean {
    val work = this.work.value

    return if (work != null) {
      favoritesRepository.addFavorite(work)
      true
    } else {
      false
    }
  }

  fun removeFromFavorites(): Boolean {
    val work = this.work.value

    return if (work != null) {
      favoritesRepository.removeFavorite(work)
      true
    } else {
      false
    }
  }
}
