

package com.subhipandey.android.bookmanstreasure.ui

import android.support.v7.widget.Toolbar

interface MainActivityDelegate {
  fun setupNavDrawer(toolbar: Toolbar)

  fun enableNavDrawer(enable: Boolean)
}