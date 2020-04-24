

package com.subhipandey.android.majesticreader.presentation

import com.subhipandey.android.majesticreader.Document

interface MainActivityDelegate {

  fun openDocument(document: Document)
}