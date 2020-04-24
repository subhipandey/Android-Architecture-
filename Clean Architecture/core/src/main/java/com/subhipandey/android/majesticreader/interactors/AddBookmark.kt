package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.BookmarkRepository
import com.subhipandey.android.majesticreader.domain.Bookmark
import com.subhipandey.android.majesticreader.domain.Document

class AddBookmark(private val bookmarkRepository: BookmarkRepository) {
  suspend operator fun invoke(document: Document, bookmark: Bookmark) =
      bookmarkRepository.addBookmark(document, bookmark)
}