package com.subhipandey.android.creaturemon.presenter

import androidx.lifecycle.LiveData
import com.subhipandey.android.creaturemon.model.Creature
import com.subhipandey.android.creaturemon.model.CreatureRepository
import com.subhipandey.android.creaturemon.model.room.RoomRepository

class AllCreaturesPresenter (private val repository: CreatureRepository = RoomRepository())
    : BasePresenter<AllCreaturesContract.View>(), AllCreaturesContract.Presenter{
    override fun getAllCreatures(): LiveData<List<Creature>> {
        return repository.getAllCreatures()
    }

    override fun clearAllCreatures() {
        repository.clearAllCreatures()
        getView()?.showCreaturesCleared()
    }
}