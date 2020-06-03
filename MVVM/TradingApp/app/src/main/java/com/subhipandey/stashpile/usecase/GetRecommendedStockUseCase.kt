

package com.subhipandey.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher

private const val GET_RECOMMENDED_STOCK_DELAY = 1_000L
private val RECOMMENDED_STOCKS = listOf("UBER", "NFLX", "SQ", "DIS", "MSFT", "BABA", "NKE", "LYFT")
private const val REFRESHING = "Refreshing..."

class GetRecommendedStockUseCase(private val ioDispatcher: CoroutineDispatcher) {

  private val _recommendedStock = MutableLiveData<String>()
  val recommendedStock: LiveData<String>
    get() = _recommendedStock

  fun get() {
  }

  fun refresh() {
  }
}