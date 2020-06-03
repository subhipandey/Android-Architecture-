

package com.subhipandey.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.delay

private const val GET_STOCKS_DELAY = 1_000L
private val STOCKS = listOf("GOOG", "AAPL", "AMZN", "TSLA", "TWTR", "FB")

class GetStocksUseCase {

  fun get(): LiveData<String> = getStocks().switchMap { stocks ->
    liveData {
      emit(stocks.joinToString())
    }
  }


  private fun getStocks(): LiveData<List<String>> = liveData {
    delay(GET_STOCKS_DELAY)
    emit(STOCKS)
  }

}