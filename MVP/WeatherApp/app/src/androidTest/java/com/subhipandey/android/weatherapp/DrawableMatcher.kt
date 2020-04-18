

package com.subhipandey.android.weatherapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class DrawableMatcher(val drawableResIds : List<Int>) : TypeSafeMatcher<View>() {

  override fun describeTo(description: Description) {
    description.appendText("with drawable from resource id: ")
    drawableResIds.forEach { drawableResId ->
      description.appendValue(drawableResId)
    }
  }

  override fun matchesSafely(item: View?): Boolean {
    return drawableResIds.any { drawableResId -> matchesDrawable(drawableResId, item) }
  }

  fun matchesDrawable(drawableResId: Int, item: View?) : Boolean {
    if (item !is ImageView) {
      return false
    }
    val target = item.drawable ?: return false
    if (drawableResId < 0) {
      return false
    }
    val expectedDrawable = ContextCompat.getDrawable(item.context, drawableResId)
        ?: return false

    if (target is BitmapDrawable) {
      val targetBitmap = (target).bitmap
      val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
      return targetBitmap.sameAs(otherBitmap)
    }
    if (target is VectorDrawable) {
      val targetBitmap = vectorToBitmap(target)
      val otherBitmap = vectorToBitmap(expectedDrawable  as VectorDrawable)
      return targetBitmap.sameAs(otherBitmap)
    }
    return false
  }

  private fun vectorToBitmap(vectorDrawable: VectorDrawable): Bitmap {
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    vectorDrawable.draw(canvas)
    return bitmap
  }
}
