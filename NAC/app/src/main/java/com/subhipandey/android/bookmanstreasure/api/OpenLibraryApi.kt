

package com.subhipandey.android.bookmanstreasure.api

import com.subhipandey.android.bookmanstreasure.data.Book
import com.subhipandey.android.bookmanstreasure.data.SearchResponse
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {

  @GET("api/books?format=json&jscmd=data")
  fun getBook(@Query("bibkeys") searchQuery: String): Call<HashMap<String, Book>>

  @GET("search.json")
  fun searchByTitle(
      @Query("title") titleQuery: String,
      @Query("page") page: Int
  ): Call<SearchResponse>

  @GET("search.json")
  fun searchByAuthor(
      @Query("author") authorQuery: String,
      @Query("page") page: Int
  ): Call<SearchResponse>

  @GET("search.json")
  fun search(
      @Query("q") searchQuery: String,
      @Query("page") page: Int
  ): Call<SearchResponse>

  companion object {
    private const val BASE_URL = "https://openlibrary.org/"
    fun create(): OpenLibraryApi = create(HttpUrl.parse(BASE_URL)!!)
    fun create(httpUrl: HttpUrl): OpenLibraryApi {
      val client = OkHttpClient.Builder()
          .build()
      return Retrofit.Builder()
          .baseUrl(httpUrl)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(OpenLibraryApi::class.java)
    }
  }
}
