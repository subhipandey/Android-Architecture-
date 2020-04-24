package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.DocumentRepository
import com.subhipandey.android.majesticreader.domain.Document

class SetOpenDocument(private val documentRepository: DocumentRepository) {
  operator fun invoke(document: Document) = documentRepository.setOpenDocument(document)
}