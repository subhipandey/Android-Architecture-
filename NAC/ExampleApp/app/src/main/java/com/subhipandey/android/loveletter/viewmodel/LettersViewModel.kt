

package com.subhipandey.android.loveletter.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.subhipandey.android.loveletter.extension.Event
import com.subhipandey.android.loveletter.extension.getOrEmpty
import com.subhipandey.android.loveletter.extension.urlEncode
import com.subhipandey.android.loveletter.helper.NotificationHelper
import com.subhipandey.android.loveletter.helper.SharedPreferenceHelper
import com.subhipandey.android.loveletter.model.FragmentType
import com.subhipandey.android.loveletter.model.Letter
import com.subhipandey.android.loveletter.model.LetterRepository
import java.util.*

class LettersViewModel(val app: Application) : AndroidViewModel(app) {

  val sentLettersLiveData: MediatorLiveData<List<Letter>> = MediatorLiveData()
  val inboxLettersLiveData: MediatorLiveData<List<Letter>> = MediatorLiveData()
  val toastLiveData: MutableLiveData<Event<String>> = MutableLiveData()

  var loading = ObservableField(View.GONE)

  var recipient = ObservableField("")
  var title = ObservableField("")
  var description = ObservableField("")
  var ps = ObservableField("")

  var profileName = ObservableField("")
  var profileEmail = ObservableField("")

  private val gson by lazy { Gson() }
  private val notificationHelper by lazy { NotificationHelper(app) }
  private val sharedPreferenceHelper by lazy { SharedPreferenceHelper(app) }
  private val letterRepository by lazy { LetterRepository(app) }

  fun saveProfile() {
    sharedPreferenceHelper.saveProfile(profileName.getOrEmpty(), profileEmail.getOrEmpty())
  }

  fun loadProfile() {
    val profile = sharedPreferenceHelper.getProfile()
    profileName.set(profile.name)
    profileEmail.set(profile.email)
  }

  fun sendLetterWithDeeplink() {
    val letter = handleSend()
    Log.i(
      "sendLetterWithDeeplink",
      "http://www.loveletter.com/letter/${gson.toJson(letter).urlEncode()}"
    )
    toastLiveData.postValue(Event("You can find letter URL in logcat"))
  }

  fun sendPushNotification() {
    val letter = handleSend()
    notificationHelper.sendLocalNotification(letter)
  }

  fun saveLetterToInbox(letter: Letter) {
    letterRepository.upsertInbox(letter)
    loadInboxLetters()
  }

  fun deleteLetter(letter: Letter, fragmentType: FragmentType) {
    letterRepository.delete(letter)
    when (fragmentType) {
      FragmentType.INBOX -> loadInboxLetters()
      FragmentType.SENT -> loadSentLetters()
    }
  }

  fun hasFullProfile(): Boolean {
    val profile = sharedPreferenceHelper.getProfile()
    return profile.name.isNotEmpty() && profile.email.isNotEmpty()
  }

  fun loadSentLetters() {
    sentLettersLiveData.addSource(letterRepository.getSent(), sentLettersLiveData::postValue)
  }

  fun loadInboxLetters() {
    inboxLettersLiveData.addSource(letterRepository.getInbox(), inboxLettersLiveData::postValue)
  }

  fun closeDb() {
    letterRepository.close()
  }

  private fun handleSend(): Letter {
    val letter = buildLetterToSend()
    letterRepository.insertSent(letter)
    loadSentLetters()
    clearLetterFields()
    return letter
  }

  private fun clearLetterFields() {
    recipient.set("")
    title.set("")
    description.set("")
    ps.set("")
  }

  private fun buildLetterToSend(): Letter {
    val letter = Letter(title.getOrEmpty(), description.getOrEmpty(), ps.getOrEmpty())
    letter.to = recipient.getOrEmpty()
    letter.sentAt = Date().time

    val profile = sharedPreferenceHelper.getProfile()
    letter.from = profile.email
    letter.fromName = profile.name
    return letter
  }

}
