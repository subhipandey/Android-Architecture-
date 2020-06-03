

package com.subhipandey.stashpile.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

private const val GET_USER_INFORMATION_DELAY = 1_000.toLong()
private const val USER_NAME = "John Doe"
private const val ACCOUNT_NUMBER = "ABCDEFG_12345"
private const val PHONE_NUMBER = "(123) 456-7890"

class GetUserInformationUseCase {

  fun get(): LiveData<UserInformation> = liveData {

    delay(GET_USER_INFORMATION_DELAY)


    emit(UserInformation(USER_NAME, ACCOUNT_NUMBER, PHONE_NUMBER))
  }

  data class UserInformation(val name: String, val accountNumber: String, val phoneNumber: String)
}