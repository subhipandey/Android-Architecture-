

package com.subhipandey.android.loveletter.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Letter::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

  abstract fun letterDao(): LetterDao
}
