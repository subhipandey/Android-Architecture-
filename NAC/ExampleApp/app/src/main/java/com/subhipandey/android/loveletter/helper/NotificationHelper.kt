

package com.subhipandey.android.loveletter.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.gson.Gson
import com.subhipandey.android.loveletter.R
import com.subhipandey.android.loveletter.model.Letter

class NotificationHelper(val context: Context) {

  companion object {
    private const val CHANNEL_ID_LETTER = "channel_letter"
    private const val CHANNEL_NAME_LETTER = "letter"
    private const val EXTRA_LETTER = "letter"
    private const val NOTIFICATION_ID_LETTER = 1
  }

  private val gson by lazy { Gson() }

  fun sendLocalNotification(letter: Letter) {
    val pendingIntent = buildPendingIntentFromNavigation(letter)
    val notification = buildLetterNotification(letter, pendingIntent!!)

    val notificationManager =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      notificationManager.createNotificationChannel(
        NotificationChannel(
          CHANNEL_ID_LETTER,
          CHANNEL_NAME_LETTER,
          NotificationManager.IMPORTANCE_HIGH
        )
      )
    }
    notificationManager.notify(NOTIFICATION_ID_LETTER, notification)
  }

  private fun buildLetterNotification(
    letter: Letter,
    pendingIntent: PendingIntent
  ): Notification? {
    val contentText = "${letter.fromName} has sent you a love letter."
    return NotificationCompat.Builder(context, CHANNEL_ID_LETTER)
      .setContentTitle(letter.title)
      .setContentText(contentText)
      .setSmallIcon(R.drawable.ic_notification)
      .setAutoCancel(true)
      .setStyle(
        NotificationCompat.BigTextStyle()
          .bigText(contentText)
      )
      .setContentIntent(pendingIntent)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setPriority(NotificationCompat.PRIORITY_MAX)
      .setDefaults(NotificationCompat.DEFAULT_ALL)
      .build()
  }

  private fun buildPendingIntentFromNavigation(letter: Letter): PendingIntent? {
    val bundle = Bundle()
    bundle.putString(EXTRA_LETTER, gson.toJson(letter))
    return NavDeepLinkBuilder(context)
      .setGraph(R.navigation.nav_graph)
      .setDestination(R.id.presentationFragment)
      .setArguments(bundle)
      .createPendingIntent()


    return null
  }
}
