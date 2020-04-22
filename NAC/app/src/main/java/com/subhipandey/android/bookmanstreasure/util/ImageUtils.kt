

package com.subhipandey.android.bookmanstreasure.util

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager

enum class CoverSize {
  S,
  M,
  L
}

fun RequestManager.loadCover(coverId: String, size: CoverSize): RequestBuilder<Drawable> {
  return this.load("http://covers.openlibrary.org/b/id/$coverId-$size.jpg")
}