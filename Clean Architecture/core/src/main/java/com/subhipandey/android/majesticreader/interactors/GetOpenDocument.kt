package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.DocumentRepository

class GetOpenDocument(private val documentRepository: DocumentRepository) {
  operator fun invoke() = documentRepository.getOpenDocument()
}
