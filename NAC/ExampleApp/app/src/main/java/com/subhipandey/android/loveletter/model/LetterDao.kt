

package com.subhipandey.android.loveletter.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LetterDao {

  @Query("SELECT * FROM letter WHERE `received_at` IS NOT NULL ORDER BY received_at DESC")
  fun getInbox(): LiveData<List<Letter>>

  @Query("SELECT * FROM letter WHERE `sent_at` IS NOT NULL ORDER BY sent_at DESC")
  fun getSent(): LiveData<List<Letter>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(letter: Letter): Long

  @Update(onConflict = OnConflictStrategy.IGNORE)
  fun update(letter: Letter)

  @Delete
  fun delete(letter: Letter)
}
