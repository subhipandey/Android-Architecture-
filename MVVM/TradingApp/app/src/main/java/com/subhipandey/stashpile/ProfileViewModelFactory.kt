
package com.subhipandey.stashpile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.subhipandey.stashpile.usecase.GetRecommendedStockUseCase
import com.subhipandey.stashpile.usecase.GetStocksUseCase
import com.subhipandey.stashpile.usecase.GetTotalValueUseCase
import com.subhipandey.stashpile.usecase.GetUserInformationUseCase

class ProfileViewModelFactory(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getTotalValueUseCase: GetTotalValueUseCase,
    private val getStocksUseCase: GetStocksUseCase,
    private val recommendedStockUseCase: GetRecommendedStockUseCase
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ProfileViewModel(getUserInformationUseCase, getTotalValueUseCase, getStocksUseCase, recommendedStockUseCase) as T
  }
}