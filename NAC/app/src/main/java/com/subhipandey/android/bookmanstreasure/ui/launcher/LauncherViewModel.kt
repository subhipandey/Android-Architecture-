
package com.subhipandey.android.bookmanstreasure.ui.launcher

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.subhipandey.android.bookmanstreasure.repository.FavoritesRepository

class LauncherViewModel(app: Application) : AndroidViewModel(app) {

  val favoriteCount: LiveData<Int> = FavoritesRepository(app).getFavoriteCount()
}
