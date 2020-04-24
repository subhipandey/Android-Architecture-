package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.DocumentRepository
import com.subhipandey.android.majesticreader.domain.Document

class AddDocument(private val documentRepository: DocumentRepository) {
  suspend operator fun invoke(document: Document) = documentRepository.addDocument(document)
}
