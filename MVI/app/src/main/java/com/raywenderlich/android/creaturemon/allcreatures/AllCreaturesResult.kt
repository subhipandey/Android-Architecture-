package com.raywenderlich.android.creaturemon.allcreatures

import com.raywenderlich.android.creaturemon.data.model.Creature
import mvibase.MviResult

sealed class AllCreaturesResult : MviResult {
    sealed class LoadAllCreaturesResult : AllCreaturesResult() {
        object Loading : LoadAllCreaturesResult()
        data class Success(val creatures: List<Creature>) : LoadAllCreaturesResult()
        data class Failure(val error: Throwable) : LoadAllCreaturesResult()
    }
    sealed class ClearAllCreaturesResult : AllCreaturesResult() {
        object Clearing : ClearAllCreaturesResult()
        data class Failure(val error: Throwable) : ClearAllCreaturesResult()

    }
}
