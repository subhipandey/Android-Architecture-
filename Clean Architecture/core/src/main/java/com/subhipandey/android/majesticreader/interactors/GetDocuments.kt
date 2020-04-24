package com.subhipandey.android.majesticreader.interactors

import com.subhipandey.android.majesticreader.data.DocumentRepository

class GetDocuments(private val documentRepository: DocumentRepository) {
  suspend operator fun invoke() = documentRepository.getDocuments()
}
