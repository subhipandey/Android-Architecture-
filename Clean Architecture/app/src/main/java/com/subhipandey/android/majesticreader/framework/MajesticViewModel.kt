

package com.subhipandey.android.majesticreader.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class MajesticViewModel(application: Application, protected val interactors: Interactors) :
    AndroidViewModel(application) {

  protected val application: MajesticReaderApplication = getApplication()

}
