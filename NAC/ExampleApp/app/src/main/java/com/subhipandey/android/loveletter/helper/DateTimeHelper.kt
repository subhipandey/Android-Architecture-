

package com.subhipandey.android.loveletter.helper

import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {
  fun parse(dateTime: Long?): String {
    return if (dateTime != null) {
      val formatter = SimpleDateFormat("yyyy-MM-dd\nhh:mm a", Locale.getDefault())
      formatter.format(Date(dateTime))
    } else {
      ""
    }
  }
}