package com.subhipandey.android.creaturemon.viewmodel

import android.arch.lifecycle.ViewModel
import com.subhipandey.android.creaturemon.model.CreatureRepository
import com.subhipandey.android.creaturemon.model.room.RoomRepository

class AllCreatureViewModel(private val repository: CreatureRepository = RoomRepository())
    : ViewModel(){

    private val allCreatureLiveData = repository.getAllCreatures()
    fun getAllCreaturesLiveData() = allCreatureLiveData
    fun clearAllCreatures() = repository.clearAllCreatures()
}