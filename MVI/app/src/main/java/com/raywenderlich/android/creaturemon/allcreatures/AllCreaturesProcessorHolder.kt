package com.raywenderlich.android.creaturemon.allcreatures

import com.raywenderlich.android.creaturemon.addcreature.AddCreatureAction
import com.raywenderlich.android.creaturemon.addcreature.AddCreatureResult
import com.raywenderlich.android.creaturemon.addcreature.AddCreatureResult.EnduranceResult
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesAction.ClearAllCreaturesAction
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesAction.LoadAllCreaturesAction
import com.raywenderlich.android.creaturemon.allcreatures.AllCreaturesResult.ClearAllCreaturesResult
import com.raywenderlich.android.creaturemon.data.model.AttributeStore
import com.raywenderlich.android.creaturemon.data.repository.CreatureRepository
import com.raywenderlich.android.creaturemon.util.schedulers.BaseSchedulerProvider
import io.reactivex.ObservableTransformer
import java.lang.IllegalArgumentException
import java.security.PrivateKey
import java.util.*

class AllCreaturesProcessorHolder(
        private val creatureRepository: CreatureRepository,
        private val schedulerProvider: BaseSchedulerProvider

){
    private val loadAllCreaturesProcessor =
            ObservableTransformer<LoadAllCreaturesAction, AllCreaturesResult.LoadAllCreaturesResult> { actions ->
                actions.flatMap {
                    creatureRepository.getAllCreatures()
                            .map { creatures -> AllCreaturesResult.LoadAllCreaturesResult.Success (creatures) }
                            .cast (AllCreaturesResult.LoadAllCreaturesResult:: class.java)
                            .onErrorReturn(AllCreaturesResult.LoadAllCreaturesResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(AllCreaturesResult.LoadAllCreaturesResult.Loading)

                }
            }
    private val clearAllCreaturesProcessor =
            ObservableTransformer<ClearAllCreaturesAction, ClearAllCreaturesResult> { actions ->
                actions.flatMap {
                    creatureRepository.clearAllCreatures()
                            .map { ClearAllCreaturesResult.Success  }
                            .cast { ClearAllCreaturesResult::class.java }
                            .onErrorReturn(ClearAllCreaturesResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(ClearAllCreaturesResult.Clearing)
                }

                private val enduranceProcessor =
                    ObservableTransformer<<EnduranceAction, EnduranceResult> { actions ->
                actions
                        .map{action ->
                            EnduranceResult.Success(
                                    AttributeStore.ENDURANCE[action.enduranceIndex].value)




                        }
                        .cast(EnduranceResult::class.java)
                        .onErrorReturn(EnduranceResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(EnduranceResult.Processing)

         }

                internal var actionProcessor =
                        ObservableTransformer<AllCreaturesAction, AllCreaturesResult> { actions ->
                            actions.publish { shared ->
                                Observable.merge(
                                        shared.ofType(LoadAllCreaturesAction::class.java).compose(loadAllCreaturesProcessor),
                                        shared.ofType(ClearAllCreaturesAction::class.java).compose(clearAllCreaturesProcessor),
                                        .mergeWith(shared.ofType(AddCreatureAction.EnduranceAction::class.java).compose(enduranceProcessor)
                                        )

                                        .mergeWith(
                                                shared.filter{v ->
                                                    v !is LoadAllCreaturesAction
                                                            && v !is ClearAllCreaturesAction
                                                }.flatMap { w ->
                                                    Observable.error<AllCreaturesResult>(
                                                            IllegalArgumentException("Unknown Action type: $w"))
                                                }

                                                }




                            }
                        }
            }
}