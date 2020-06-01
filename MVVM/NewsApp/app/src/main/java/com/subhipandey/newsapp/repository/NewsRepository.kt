package com.subhipandey.newsapp.repository

import com.subhipandey.newsapp.api.RetrofitInstance
import com.subhipandey.newsapp.db.ArticleDatabase
import retrofit2.Retrofit

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}