package com.raywenderlich.android.creaturemon.allcreatures

import android.database.Observable
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesAction.*
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesIntent.*
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesResult.*
import com.raywenderlich.android.creaturemon.util.notOfType
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import mvibase.MviViewModel
import java.util.function.BiFunction

class  AllCreaturesViewModel(
        private val actionProcessorHolder: AllCreaturesProcessorHolder
) : ViewModel(), MviViewModel<AllCreaturesIntent, AllCreaturesViewState> {

    private val intentsSubject: PublishSubject<AllCreaturesIntent> = PublishSubject.create()
    private val statesObservable: Observable<AllCreaturesViewState> = Compose()

    private val intentFilter: ObservableTransformer<AllCreaturesIntent, AllCreaturesIntent>
    get() = ObservableTransformer { intents ->
        intents.publish{ shared ->
            Observable.merge(
                    shared.ofType(LoadAllCreaturesIntent::class.java).take(1),
                    shared.notOfType(LoadAllCreaturesIntent::class.java)
            )
        }
    }



    override fun processIntents(intents: Observable<AllCreaturesIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<AllCreaturesViewState> = statesObservable

    private fun compose(): Observable<AllCreaturesViewState> {
        return intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(AllCreaturesViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: AllCreaturesIntent): AllCreaturesAction {
        return when (intent) {
            is LoadAllCreaturesIntent -> LoadAllCreaturesAction
            is ClearAllCreaturesIntent -> ClearAllCreaturesAction
        }
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