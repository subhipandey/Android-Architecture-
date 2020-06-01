package com.subhipandey.newsapp.models

data class NewsResponse(
    val sources: List<Source>,
    val status: String,
    val totalResults: Int
)