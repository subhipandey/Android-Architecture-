
package com.subhipandey.android.bookmanstreasure.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.subhipandey.android.bookmanstreasure.data.Work

@Dao
interface FavoritesDao {

  @Query("SELECT * FROM Work")
  fun getFavorites(): DataSource.Factory<Int, Work>

  @Query("SELECT * FROM Work WHERE id = :id ")
  fun getFavorite(id: String): LiveData<Work>

  @Query("SELECT count(*) FROM Work")
  fun getFavoriteCount(): LiveData<Int>

  @Insert(onConflict = REPLACE)
  fun addFavorite(work: Work)

  @Delete
  fun removeFavorite(work: Work)
}
