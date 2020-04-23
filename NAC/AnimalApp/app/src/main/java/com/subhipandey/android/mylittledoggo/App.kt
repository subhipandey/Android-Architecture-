

package com.subhipandey.android.mylittledoggo

import android.app.Application
import androidx.room.Room
import com.subhipandey.android.mylittledoggo.data.DogCeoDoggoRepository
import com.subhipandey.android.mylittledoggo.data.api.Api
import com.subhipandey.android.mylittledoggo.data.api.ConnectionManager
import com.subhipandey.android.mylittledoggo.data.cache.DoggosDatabase
import com.subhipandey.android.mylittledoggo.data.cache.RoomCache
import com.subhipandey.android.mylittledoggo.domain.DoggoRepository
import com.subhipandey.android.mylittledoggo.presentation.doggodetail.DoggoViewModelFactory
import com.subhipandey.android.mylittledoggo.presentation.doggos.DoggoListViewModelFactory
import com.subhipandey.android.mylittledoggo.presentation.favorites.FavoritesViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class App : Application() {

  override fun onCreate() {
    super.onCreate()

    val repository = createRepository()
    ConnectionManager.setContext(this)

    DoggoListViewModelFactory.inject(repository)
    DoggoViewModelFactory.inject(repository)
    FavoritesViewModelFactory.inject(repository)
  }

  private fun createRepository(): DoggoRepository {
    val doggosApi = Api.create()
    val database = createDatabase()
    val doggosDao = database.doggosDao()
    val doggosCache = RoomCache(doggosDao)

    return DogCeoDoggoRepository(doggosApi, doggosCache)
  }

  private fun createDatabase(): DoggosDatabase =
      Room.databaseBuilder(this, DoggosDatabase::class.java, "myLittleDoggo.db")
          .build()
}