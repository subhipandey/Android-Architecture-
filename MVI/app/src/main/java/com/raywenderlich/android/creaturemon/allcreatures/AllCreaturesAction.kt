package com.raywenderlich.android.creaturemon.allcreatures

import mvibase.MviAction

sealed class AllCreaturesAction :   MviAction {
    object LoadAllCreaturesAction : AllCreaturesAction()
    object ClearAllCreaturesAction : AllCreaturesAction()
}