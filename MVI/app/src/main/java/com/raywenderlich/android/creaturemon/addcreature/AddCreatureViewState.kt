package com.raywenderlich.android.creaturemon.addcreature

import com.raywenderlich.android.creaturemon.data.model.Creature
import com.raywenderlich.android.creaturemon.data.model.CreatureAttributes
import com.raywenderlich.android.creaturemon.data.model.CreatureGenerator
import mvibase.MviViewState

data class AddCreatureViewState(
        val isProcessing: Boolean,
        val creature: Creature,
        val isDrawableSelected: Boolean,
        val isSaveComplete: Boolean,
        val error: Throwable?

): MviViewState {
    companion object {
        fun default(): AddCreatureViewState = AddCreatureViewState(
                false,
                CreatureGenerator().generateCreature(CreatureAttributes(), name = "", drawable = 0),
                false,
                false,
                null

        )
    }
}