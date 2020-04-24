package com.subhipandey.android.majesticreader.data

import com.subhipandey.android.majesticreader.domain.Document

interface DocumentDataSource {

  suspend fun add(document: Document)

  suspend fun readAll(): List<Document>

  suspend fun remove(document: Document)
}