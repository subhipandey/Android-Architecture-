

package com.subhipandey.android.creaturemon.model

import androidx.lifecycle.LiveData

interface CreatureRepository{
    fun saveCreature(creature: Creature)
    fun getAllCreatures(): LiveData<List<Creature>>
    fun clearAllCreatures()
}