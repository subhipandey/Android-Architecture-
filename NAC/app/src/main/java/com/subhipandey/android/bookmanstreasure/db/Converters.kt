

package com.subhipandey.android.bookmanstreasure.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

  @TypeConverter
  fun fromJson(json: String): List<String> =
      Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)

  @TypeConverter
  fun toJson(list: List<String>): String = Gson().toJson(list)
}