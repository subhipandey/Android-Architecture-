
package com.subhipandey.android.bookmanstreasure.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.subhipandey.android.bookmanstreasure.data.Work

/**
 * Favorites database schema.
 */
@Database(entities = [Work::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
  companion object {

    private const val DATABASE_NAME = "favorites.db"

    fun create(context: Context): FavoritesDatabase =
        Room.databaseBuilder(context, FavoritesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
  }

  abstract fun favoritesDao(): FavoritesDao
}
