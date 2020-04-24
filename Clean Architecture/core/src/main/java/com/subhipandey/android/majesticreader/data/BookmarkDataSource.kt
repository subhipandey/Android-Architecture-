package com.subhipandey.android.majesticreader.data

import com.subhipandey.android.majesticreader.domain.Bookmark
import com.subhipandey.android.majesticreader.domain.Document

interface BookmarkDataSource {

  suspend fun add(document: Document, bookmark: Bookmark)

  suspend fun read(document: Document): List<Bookmark>

  suspend fun remove(document: Document, bookmark: Bookmark)
}