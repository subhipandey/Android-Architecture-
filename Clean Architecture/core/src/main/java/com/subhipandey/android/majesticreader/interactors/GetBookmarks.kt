package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.BookmarkRepository
import com.subhipandey.android.majesticreader.domain.Document

class GetBookmarks(private val bookmarkRepository: BookmarkRepository) {

  suspend operator fun invoke(document: Document) = bookmarkRepository.getBookmarks(document)
}