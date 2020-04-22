

package com.subhipandey.android.loveletter.extension

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import java.net.URLDecoder
import java.net.URLEncoder

fun Fragment.hideKeyboard() {
  this.activity?.apply {
    if (currentFocus != null) {
      val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(view?.windowToken, 0)
    } else {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
  }
}

fun String.urlEncode(): String = URLEncoder.encode(this, "utf8")

fun String.urlDecode(): String = URLDecoder.decode(this, "utf8")

fun View.getString(id: Int, vararg formatArg: String): String = this.context.resources.getString(
  id,
  *formatArg
)

fun ObservableField<String>.getOrEmpty(): String = this.get() ?: ""
