

package com.subhipandey.android.loveletter.helper

import android.content.Context
import com.google.gson.Gson
import com.subhipandey.android.loveletter.model.Profile

class SharedPreferenceHelper(context: Context) {
  companion object {
    const val NAME = "love_letter"
    const val PROFILE = "profile"
  }

  private val gson by lazy { Gson() }

  private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

  fun getProfile(): Profile {
    val jsonString = sharedPreferences.getString(PROFILE, null)
    return if (jsonString == null) {
      Profile()
    } else {
      gson.fromJson(jsonString, Profile::class.java)
    }
  }

  fun saveProfile(name: String, email: String) {
    with(sharedPreferences.edit()) {
      putString(PROFILE, gson.toJson(Profile(name, email)))
      apply()
    }
  }
}