

package com.subhipandey.android.watchlist

import android.app.Application

class WatchlistApp : Application() {
  val watchlistRepository by lazy {
    WatchlistRepository()
  }
}