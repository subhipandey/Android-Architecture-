

package com.subhipandey.android.majesticreader.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object MajesticViewModelFactory : ViewModelProvider.Factory {

  lateinit var application: Application

  lateinit var dependencies: Interactors

  fun inject(application: Application, dependencies: Interactors) {
    MajesticViewModelFactory.application = application
    MajesticViewModelFactory.dependencies = dependencies
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(MajesticViewModel::class.java.isAssignableFrom(modelClass)) {
      return modelClass.getConstructor(Application::class.java, Interactors::class.java)
          .newInstance(
              application,
              dependencies)
    } else {
      throw IllegalStateException("ViewModel must extend MajesticViewModel")
    }
  }

}
