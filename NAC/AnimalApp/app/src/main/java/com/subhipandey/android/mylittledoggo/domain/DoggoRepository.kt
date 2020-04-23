

package com.subhipandey.android.mylittledoggo.domain

import kotlinx.coroutines.flow.Flow

interface DoggoRepository {

  fun getCachedDoggos(): Flow<List<Doggo>>
  fun getCachedFavoriteDoggos(): Flow<List<Doggo>>
  suspend fun getApiDoggos(numberOfImages: Int = 10): List<Doggo>
  suspend fun updateCachedDoggos(doggos: List<Doggo>)
  suspend fun updateDoggoFavoriteStatus(doggoPicture: String, isFavorite: Boolean)
}