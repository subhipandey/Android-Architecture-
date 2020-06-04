

package com.subhipandey.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val GET_RECOMMENDED_STOCK_DELAY = 1_000L
private val RECOMMENDED_STOCKS = listOf("UBER", "NFLX", "SQ", "DIS", "MSFT", "BABA", "NKE", "LYFT")
private const val REFRESHING = "Refreshing..."

class GetRecommendedStockUseCase(private val ioDispatcher: CoroutineDispatcher) {

  private val _recommendedStock = MutableLiveData<String>()
  val recommendedStock: LiveData<String>
    get() = _recommendedStock

  suspend fun get() = withContext(ioDispatcher) {
    delay(GET_RECOMMENDED_STOCK_DELAY)


    _recommendedStock.postValue(RECOMMENDED_STOCKS.random())
  }


  suspend fun refresh() = withContext(ioDispatcher) {
    _recommendedStock.postValue(REFRESHING)
    get()
  }

}