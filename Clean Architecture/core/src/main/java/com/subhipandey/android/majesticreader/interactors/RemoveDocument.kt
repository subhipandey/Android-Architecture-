package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.DocumentRepository
import com.subhipandey.android.majesticreader.domain.Document

class RemoveDocument(private val documentRepository: DocumentRepository) {
  suspend operator fun invoke(document: Document) = documentRepository.removeDocument(document)
}