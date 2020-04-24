package com.subhipandey.android.majesticreader.data

import com.subhipandey.android.majesticreader.domain.Document

interface OpenDocumentDataSource {

  fun setOpenDocument(document: Document)

  fun getOpenDocument(): Document
}