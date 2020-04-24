package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.BookmarkRepository
import com.subhipandey.android.majesticreader.domain.Bookmark
import com.subhipandey.android.majesticreader.domain.Document

class RemoveBookmark(private val bookmarksRepository: BookmarkRepository) {
  suspend operator fun invoke(document: Document, bookmark: Bookmark) = bookmarksRepository
      .removeBookmark(document, bookmark)
}