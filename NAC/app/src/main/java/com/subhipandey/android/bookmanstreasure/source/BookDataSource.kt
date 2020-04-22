

package com.subhipandey.android.bookmanstreasure.source

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.subhipandey.android.bookmanstreasure.api.OpenLibraryApi
import com.subhipandey.android.bookmanstreasure.data.Book
import com.subhipandey.android.bookmanstreasure.data.Work
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BookDataSource(
    private val openLibraryApi: OpenLibraryApi,
    private val work: Work?
) : PageKeyedDataSource<Int, Book>() {

  val networkState = MutableLiveData<NetworkState>()

  fun getNextPageKey(startIndex: Int, count: Int): Int? {
    return if (startIndex + count < work?.editionIsbns?.size ?: 0) {
      (startIndex + count)
    } else {
      null
    }
  }

  private fun createRequest(startIndex: Int, count: Int): Call<HashMap<String, Book>> {
    val isbns = work?.editionIsbns ?: ArrayList()

    val endIndex = if (isbns.size < startIndex + count) {
      isbns.size
    } else {
      startIndex + count
    }

    val query = if (!isbns.isEmpty()) {
      isbns.subList(startIndex, endIndex).map { "ISBN:$it" }.reduce { acc, s -> "$acc,$s" }
    } else {
      ""
    }

    return openLibraryApi.getBook(query)
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
    // Ignored, since we only ever append to our initial load
  }

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Book>) {
    networkState.postValue(NetworkState.LOADING)

    try {
      val response = createRequest(0, params.requestedLoadSize)
          .execute()

      val data = response.body()
      val items = data?.values?.toList() ?: ArrayList()

      networkState.postValue(NetworkState.LOADED)

      callback.onResult(
          items,
          null,
          getNextPageKey(0, params.requestedLoadSize)
      )
    } catch (ioException: IOException) {
      ioException.printStackTrace()

      val error = NetworkState.error(ioException.message ?: "unknown error")
      networkState.postValue(error)
    }
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
    createRequest(params.key, params.requestedLoadSize)
        .enqueue(object : Callback<HashMap<String, Book>> {

          override fun onResponse(call: Call<HashMap<String, Book>>?, response: Response<HashMap<String, Book>>?) {
            if (response?.isSuccessful == true) {
              val data = response.body()
              val items = data?.values?.toList() ?: ArrayList()

              callback.onResult(
                  items,
                  getNextPageKey(params.key, params.requestedLoadSize)
              )
            }
          }

          override fun onFailure(call: Call<HashMap<String, Book>>?, t: Throwable?) {
            networkState.postValue(NetworkState.error(t?.message ?: "unknown err"))
          }
        })
  }
}
