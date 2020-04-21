

package com.subhipandey.android.creaturemon.model.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.subhipandey.android.creaturemon.model.Creature

@Dao
interface CreatureDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(creature: Creature)

    @Query("SELECT * FROM creature_table ORDER BY name ASC")
    fun getAllCreatures(): LiveData<List<Creature>>


}