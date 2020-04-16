

package com.subhipandey.android.creaturemon.model.room

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.subhipandey.android.creaturemon.app.CreaturemonApplication
import com.subhipandey.android.creaturemon.model.Creature
import com.subhipandey.android.creaturemon.model.CreatureRepository

class RoomRepository : CreatureRepository {
  private val creatureDao: CreatureDao = CreaturemonApplication.database.creatureDao()

  private val allCreatures: LiveData<List<Creature>>

  init {
    allCreatures = creatureDao.getAllCreatures()
  }

  override fun saveCreature(creature: Creature) {
    InsertAsyncTask(creatureDao).execute(creature)
  }

  override fun getAllCreatures() = allCreatures

  override fun clearAllCreatures() {
    val creatureArray = allCreatures.value?.toTypedArray()
    if (creatureArray != null) {
      DeleteAsyncTask(creatureDao).execute(*creatureArray)
    }
  }

  private class InsertAsyncTask internal constructor(private val dao: CreatureDao) : AsyncTask<Creature, Void, Void>() {
    override fun doInBackground(vararg params: Creature): Void? {
      dao.insert(params[0])
      return null
    }
  }

  private class DeleteAsyncTask internal constructor(private val dao: CreatureDao) : AsyncTask<Creature, Void, Void>() {
    override fun doInBackground(vararg params: Creature): Void? {
      dao.clearCreatures(*params)
      return null
    }
  }
}