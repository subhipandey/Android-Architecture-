package com.raywenderlich.android.creaturemon.addcreature

import android.database.Observable
import androidx.constraintlayout.solver.widgets.ConstraintAnchor
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.addcreature.AddCreatureResult.*
import com.raywenderlich.android.creaturemon.data.model.CreatureAttributes
import com.raywenderlich.android.creaturemon.data.model.CreatureGenerator
import mvibase.MviViewModel
import java.util.function.BiFunction

class AddCreatureViewModel (
        private val actionProcessHolder: AddCreatureProcessorHolder
) : ViewModel(), MviViewModel<AddCreatureIntent, AddCreatureViewState> {
    override fun processIntents(intents: Observable<AddCreatureIntent>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun states(): Observable<AddCreatureViewState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val generator = CreatureGenerator()

        private val reducer = BiFunction{ previousState: AddCreatureViewState, result: AddCreatureResult ->
        when(result){
            is AvatarResult -> reduceAvatar(previousState, result)
            is NameResult -> reduceName(previousState, result)
            is IntelligenceResult -> reduceIntelligence(previousState, result)
            is StrengthResult -> reduceStrength(previousState, result)
            is EnduranceResult -> reduceEndurance(previousState, result)
            is SaveResult -> reduceSave(previousState, result)
        }
        }
        private fun reduceAvatar(
                previousState: AddCreatureViewState,
                result: AvatarResult)
                : AddCreatureViewState = when (result) {
            is AvatarResult.Success -> {
                previousState.copy(
                        isProcessing = false,
                        error = null,
                        creature = generator.generateCreature(
                                previousState.creature.attributes, previousState.creature.name, result.drawable),
                        isDrawableSelected = (result.drawable != 0))
            }
            is AvatarResult.Failure -> {
                previousState.copy(isProcessing = false, error = result.error)
            }
            is AvatarResult.Processing -> {
                previousState.copy(isProcessing = true, error = null)
            }

        }
        private fun reduceName(
        previousState: AddCreatureViewState,
        result: NameResult)
        : AddCreatureViewState = when(result) {
            is NameResult.Success -> {
                previousState.copy(
                        isProcessing = false,
                        error = null,
                        creature = generator.generateCreature(
                                previousState.creature.attributes,result.name, previousState.creature.drawable))
            }
            is NameResult.Failure -> {
                previousState.copy(isProcessing = false, error = result.error)
            }
            is NameResult.Processing -> {
                previousState.copy(isProcessing = true, error = null)

            }
        }

        private fun reduceIntelligence(
                previousState: AddCreatureViewState,
                result: IntelligenceResult)
        : AddCreatureViewState = when(result) {
            is IntelligenceResult.Success -> {
                val attributes = CreatureAttributes(
                        result.intelligence,
                        previousState.creature.attributes.strength,
                        previousState.creature.attributes.endurance)
                previousState.copy(
                        isProcessing = false,
                        error = null,
                        creature = generator.generateCreature(
                                attributes, previousState.creature.name, previousState.creature.drawable))

            }
            is IntelligenceResult.Failure -> {
                previousState.copy(isProcessing = false, error = result.error)
            }
            is IntelligenceResult.Processing -> {
                previousState.copy(isProcessing = true, error = null)
            }
        }

    private  fun  reduceStrength(
            previousState: AddCreatureViewState,
            result: StrengthResult)
        : AddCreatureViewState = when(result) {
        is StrengthResult.Success -> {
            val attributes = CreatureAttributes(
                    previousState.creature.attributes.intelligence,
                    result.strength,
                    previousState.creature.attributes.endurance)
            previousState.copy(
                    isProcessing = false,
                    error = null,
                    creature = generator.generateCreature(
                            attributes, previousState.creature.name, previousState.creature.drawable))

        }
        is StrengthResult.Failure -> {
            previousState.copy(isProcessing = false, error = result.error)
        }
        is StrengthResult.Processing -> {
            previousState.copy(isProcessing = true, error = null)
        }

    }

        private fun reduceEndurance (
                previousState: AddCreatureViewState,
                result: EnduranceResult)
                :AddCreatureViewState = when (result) {
            is EnduranceResult.Success -> {
                val attributes = CreatureAttributes(
                        previousState.creature.attributes.intelligence,
                        previousState.creature.attributes.strength,
                        result.endurance
                )
                previousState.copy(
                        isProcessing = false,
                        error = null,
                        creature = generator.generateCreature(
                                attributes, previousState.creature.name, previousState.creature.drawable))

            }
            is EnduranceResult.Failure -> {
                previousState.copy(isProcessing = false, error = result.error)
            }
            is EnduranceResult.Processing -> {
                previousState.copy(isProcessing = true, error = null)
            }
        }

        private fun reduceSave(
                previousState: AddCreatureViewState,
                result: SaveResult)
        : AddCreatureViewState = when(result) {
            is SaveResult.Success -> {
                previousState.copy(isProcessing = false, isSaveComplete = true, error = null)

            }
            is SaveResult.Failure -> {
                previousState.copy(isProcessing = false, error = result.error)
            }
            is SaveResult.Processing -> {
                previousState.copy(isProcessing = true, error = null)
            }
        }
    }




}