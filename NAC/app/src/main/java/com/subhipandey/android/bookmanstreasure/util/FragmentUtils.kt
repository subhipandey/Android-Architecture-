

package com.subhipandey.android.bookmanstreasure.util

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

fun Fragment.initToolbar(toolbar: Toolbar, titleResId: Int, backEnabled: Boolean) {
  val appCompatActivity = activity as AppCompatActivity
  appCompatActivity.setSupportActionBar(toolbar)
  appCompatActivity.supportActionBar?.setTitle(titleResId)
  appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
}
