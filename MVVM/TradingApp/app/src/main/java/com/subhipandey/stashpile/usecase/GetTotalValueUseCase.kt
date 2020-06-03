

package com.subhipandey.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import kotlin.random.Random

private const val INITIAL_TOTAL_VALUE = 15_000.00
private const val TOTAL_VALUE_DIFF_RANGE = 5.00
private const val TOTAL_VALUE_INITIAL_DELAY_MS = 1_000L
private const val TOTAL_VALUE_UPDATE_RATE_MS = 2_000L

class GetTotalValueUseCase {

  fun get(): LiveData<Double> = liveData {

    emit(0.00)


    delay(TOTAL_VALUE_INITIAL_DELAY_MS)


    emitSource(getTotalValue())
  }

  private fun getTotalValue(): LiveData<Double> = liveData {
    var total = INITIAL_TOTAL_VALUE

    // 4
    while (true) {
      // 5
      delay(TOTAL_VALUE_UPDATE_RATE_MS)

      // 6
      total = total.moreOrLessWithMargin(TOTAL_VALUE_DIFF_RANGE)
      emit(total)
    }
  }

  private fun Double.moreOrLessWithMargin(margin: Double): Double {
    val diff = Random.nextDouble(-margin, margin)
    return this + diff
  }
}