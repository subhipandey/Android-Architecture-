package com.raywenderlich.android.creaturemon.allcreatures

import mvibase.MviIntent

sealed class  AllCreaturesIntent : MviIntent {
    object LoadAllCreaturesIntent: AllCreaturesIntent()
    object ClearAllCreaturesIntent: AllCreaturesIntent()
}