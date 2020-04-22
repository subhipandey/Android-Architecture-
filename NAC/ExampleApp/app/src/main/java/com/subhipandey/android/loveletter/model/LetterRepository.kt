

package com.subhipandey.android.loveletter.model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*

class LetterRepository(val application: Application) {

  private val db = Room.databaseBuilder(
    application,
    AppDatabase::class.java, "love-letter"
  ).build()

  fun getInbox(): LiveData<List<Letter>> {
    return db.letterDao().getInbox()
  }

  fun getSent(): LiveData<List<Letter>> {
    return db.letterDao().getSent()
  }

  fun insertSent(letter: Letter) {
    letter.sentAt = Date().time
    AsyncTask.execute {
      db.letterDao().insert(letter)
    }
  }

  fun upsertInbox(letter: Letter) {
    letter.receivedAt = Date().time
    AsyncTask.execute {
      val id = db.letterDao().insert(letter)
      if (id == -1L) db.letterDao().update(letter)
    }
  }

  fun delete(letter: Letter) {
    AsyncTask.execute {
      db.letterDao().delete(letter)
    }
  }

  fun close() {
    if (db.isOpen) db.close()
  }
}
