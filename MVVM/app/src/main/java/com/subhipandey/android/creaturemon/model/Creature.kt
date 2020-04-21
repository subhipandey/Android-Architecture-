package com.subhipandey.android.creaturemon.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "creature_table")
data class Creature (
        val attributes: CreatureAttributes = CreatureAttributes(),
        val hitPoints: Int = 0,
        @PrimaryKey @NotNull val name: String = "",
        val drawable: Int = 0
)