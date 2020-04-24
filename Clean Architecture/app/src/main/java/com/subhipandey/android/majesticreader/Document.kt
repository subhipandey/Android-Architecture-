

package com.subhipandey.android.majesticreader

import java.io.Serializable

data class Document(
    val url: String,
    val name: String,
    val size: Int,
    val thumbnail: String
): Serializable {
  companion object {
    val EMPTY = Document("", "", 0, "")
  }
}