

package com.subhipandey.android.bookmanstreasure.repository

import android.app.Application
import com.subhipandey.android.bookmanstreasure.data.Work
import com.subhipandey.android.bookmanstreasure.db.FavoritesDao
import com.subhipandey.android.bookmanstreasure.db.FavoritesDatabase
import org.jetbrains.anko.doAsync

class FavoritesRepository(app: Application) {

  private val favoritesDao: FavoritesDao = FavoritesDatabase.create(app).favoritesDao()

  fun getFavorites() = favoritesDao.getFavorites()

  fun getFavorite(id: String) = favoritesDao.getFavorite(id)

  fun getFavoriteCount() = favoritesDao.getFavoriteCount()

  fun addFavorite(work: Work) {
    doAsync {
      favoritesDao.addFavorite(work)
    }
  }

  fun removeFavorite(work: Work) {
    doAsync {
      favoritesDao.removeFavorite(work)
    }
  }
}
