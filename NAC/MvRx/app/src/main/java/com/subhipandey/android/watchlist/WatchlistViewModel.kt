package com.subhipandey.android.watchlist

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext

class WatchlistViewModel(
        initialState: WatchlistState,
        private val watchlistRepository: WatchlistRepository
) : BaseMvRxViewModel<WatchlistState>(initialState, debugMode = true) {

    companion object : MvRxViewModelFactory<WatchlistViewModel, WatchlistState> {

        override fun create(viewModelContext: ViewModelContext,
                            state: WatchlistState): WatchlistViewModel? {
            val watchlistRepository =
                    viewModelContext.app<WatchlistApp>().watchlistRepository
            return WatchlistViewModel(state, watchlistRepository)
        }
    }
}
