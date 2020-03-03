package com.raywenderlich.android.creaturemon.allcreatures

import android.database.Observable
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesResult.*
import mvibase.MviViewModel
import java.util.function.BiFunction

class  AllCreaturesViewModel(
        private val actionProcessorHolder: AllCreaturesProcessorHolder
) : ViewModel(), MviViewModel<AllCreaturesIntent, AllCreaturesViewState> {
    override fun processIntents(intents: Observable<AllCreaturesIntent>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun states(): Observable<AllCreaturesViewState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private  val reducer = BiFunction{ previousState: AllCreaturesViewState, result: AllCreaturesResult ->
            when (result) {
                is LoadAllCreaturesResult -> when(result) {
                    is LoadAllCreaturesResult.Success -> {
                        previousState.copy(isLoading = false, creatures = result.creatures)

                    }
                    is LoadAllCreaturesResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                    is LoadAllCreaturesResult.Loading -> previousState.copy(isLoading = true)
                }
                is ClearAllCreaturesResult -> when (result) {
                    is ClearAllCreaturesResult.Success -> {
                        previousState.copy(isLoading = false, creatures = emptyList())
                    }
                    is ClearAllCreaturesResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                        is ClearAllCreaturesResult.Clearing -> previousState.copy(isLoading = true)

                }
            }
        }
    }


}