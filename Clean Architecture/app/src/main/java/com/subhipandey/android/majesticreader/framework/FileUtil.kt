

package com.subhipandey.android.majesticreader.framework

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtil {

  data class DocumentDetail(val name: String, val size: Int, val thumbnail: String)

  private fun getPdfThumbnailUri(context: Context, documentUri: String, documentName: String):
      String {
    val pdfRenderer = PdfRenderer(context.contentResolver.openFileDescriptor(Uri.parse(documentUri),
        "r"))

    val firstPage = pdfRenderer.openPage(0)

    val bitmap = Bitmap.createBitmap(
        firstPage.width,
        firstPage.height,
        Bitmap.Config.ARGB_8888)

    firstPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
    firstPage.close()
    pdfRenderer.close()

    // Write bitmap
    val thumbnailFile = File(context.cacheDir, documentName + "_thumbnail")

    try {
      FileOutputStream(thumbnailFile).use { out: FileOutputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
      }
    } catch (e: IOException) {
      e.printStackTrace()
    }

    return thumbnailFile.absolutePath
  }

  fun getDocumentDetails(context: Context, documentUri: String): DocumentDetail {
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.SIZE)

    context.contentResolver.query(Uri.parse(documentUri), projection, null, null, null)?.use {
      metaCursor ->
      val nameIndex = metaCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
      val sizeIndex = metaCursor.getColumnIndex(OpenableColumns.SIZE)

      return if (metaCursor.moveToFirst()) {
        val name = metaCursor.getString(nameIndex)

        DocumentDetail(
            name,
            metaCursor.getInt(sizeIndex),
            getPdfThumbnailUri(context,
                documentUri, name)
        )
      } else {
        DocumentDetail(
            "No name",
            0,
            ""
        )
      }
    } ?: return DocumentDetail(
        "No name", 0, "")
  }

}