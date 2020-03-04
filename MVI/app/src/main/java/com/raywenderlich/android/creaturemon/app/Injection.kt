package com.raywenderlich.android.creaturemon.app

import android.content.Context
import com.raywenderlich.android.creaturemon.data.model.CreatureGenerator
import com.raywenderlich.android.creaturemon.data.repository.CreatureRepository
import com.raywenderlich.android.creaturemon.data.repository.room.RoomRepository
import com.raywenderlich.android.creaturemon.util.schedulers.BaseSchedulerProvider
import com.raywenderlich.android.creaturemon.util.schedulers.SchedulerProvider
import java.security.AccessControlContext

object injection {
    fun provideCreatureRepository(context: Context): CreatureRepository {
        return RoomRepository()
    }
    fun provideCreatureGenerator(): CreatureGenerator {
        return CreatureGenerator()
    }

    fun provideSchedulerProvider() : BaseSchedulerProvider = SchedulerProvider
}