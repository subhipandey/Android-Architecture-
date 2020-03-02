package com.raywenderlich.android.creaturemon.addcreature

import com.raywenderlich.android.creaturemon.addcreature.AddCreatureAction.*
import com.raywenderlich.android.creaturemon.addcreature.AddCreatureResult.*
import com.raywenderlich.android.creaturemon.data.model.AttributeStore
import com.raywenderlich.android.creaturemon.data.model.CreatureAttributes
import com.raywenderlich.android.creaturemon.data.model.CreatureGenerator
import com.raywenderlich.android.creaturemon.data.repository.CreatureRepository
import com.raywenderlich.android.creaturemon.util.schedulers.BaseSchedulerProvider
import io.reactivex.ObservableTransformer
import java.lang.IllegalArgumentException
import java.util.*

class AddCreatureProcessorHolder (
        private val creatureRepository: CreatureRepository,
        private val creatureGenerator: CreatureGenerator,
        private val schedulerProvider: BaseSchedulerProvider
) {
    private val avatarProcessor =
            ObservableTransformer<AvatarAction, AvatarResult> { actions ->
                actions
                        .map { action -> AvatarResult.Success(action.drawable) }
                        .cast(AvatarResult::class.java)
                        .onErrorReturn(AvatarResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(AvatarResult.Processing)
            }
    private val nameProcessor =
            ObservableTransformer<NameAction, NameResult> { actions ->
                actions
                        .map { action -> NameResult.Success(action.name) }
                        .cast(NameResult::class.java)
                        .onErrorReturn(NameResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(NameResult.Processing)
            }
    private val intelligenceProcessor =
            ObservableTransformer<IntelligenceAction, IntelligenceResult> { actions ->
                actions
                        .map { action -> IntelligenceResult.Success(AttributeStore.INTELLIGENCE[action.intelligenceIndex].value)

                        }
                        .cast(IntelligenceResult::class.java)
                        .onErrorReturn(IntelligenceResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(IntelligenceResult.Processing)

            }
    private val strengthProcessor =
            ObservableTransformer<StrengthAction, StrengthResult> { actions ->
                actions
                        .map { action ->
                            StrengthResult.Success(
                                    AttributeStore.STRENGTH[action.strengthIndex].value)

                        }
                        .cast(StrengthResult::class.java)
                        .onErrorReturn(StrengthResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(StrengthResult.Processing)

            }
    private val saveProcessor =
            ObservableTransformer<SaveAction, SaveResult> { actions ->
                actions.flatMap{ action ->
                    val attributes = CreatureAttributes(
                            AttributeStore.INTELLIGENCE[action.intelligenceIndex].value,
                            AttributeStore.STRENGTH[action.strengthIndex].value,
                            AttributeStore.ENDURANCE[action.enduranceIndex].value)
                    val creature = creatureGenerator.generateCreature(attributes, action.name, action.drawable)
                    creatureRepository.saveCreature(creature)
                            .map {SaveResult.Success}
                            .cast(SaveResult::class.java)
                            .onErrorReturn(SaveResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(SaveResult.Processing)


                }
            }
    internal var actionProcessor =
            ObservableTransformer<AddCreatureAction, AddCreatureResult> { actions ->
                actions.publish { shared ->
                    Observable.merge(
                            shared.ofType(AvatarAction::class.java).compose(avatarProcessor),
                            shared.ofType(NameAction::class.java).compose(nameProcessor),
                            shared.ofType(IntelligenceAction::class.java).compose(intelligenceProcessor),
                            shared.ofType(StrengthAction::class.java).compose(strengthProcessor))
                            .mergeWith(shared.ofType(SaveAction::class.java).compose(saveProcessor))
                            .mergeWith(
                                    shared.filter { v ->
                                        v !is AvatarAction
                                                && v !is NameAction
                                                && v !is IntelligenceAction
                                                && v !is StrengthAction
                                                && v !is SaveAction
                                    }.flatMap { w ->
                                       Observable.error<AddCreatureResult>{
                                           IllegalArgumentException("Unknown Action Type: $w")
                                    }
                                    }

                    )
                }
            }
}