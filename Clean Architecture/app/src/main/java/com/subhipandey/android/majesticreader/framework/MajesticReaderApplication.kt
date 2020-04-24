

package com.subhipandey.android.majesticreader.framework

import android.app.Application

class MajesticReaderApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    MajesticViewModelFactory.inject(
        this,
        Interactors()
    )
  }
}