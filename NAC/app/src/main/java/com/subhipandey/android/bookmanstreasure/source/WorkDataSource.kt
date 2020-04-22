

package com.subhipandey.android.bookmanstreasure.source

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.subhipandey.android.bookmanstreasure.api.OpenLibraryApi
import com.subhipandey.android.bookmanstreasure.data.SearchCriteria
import com.subhipandey.android.bookmanstreasure.data.SearchResponse
import com.subhipandey.android.bookmanstreasure.data.Work
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WorkDataSource(
    private val openLibraryApi: OpenLibraryApi,
    private val searchTerm: String,
    private val searchCriteria: SearchCriteria
) : PageKeyedDataSource<Int, Work>() {

  val networkState = MutableLiveData<NetworkState>()

  fun getNextPageKey(firstItem: Int, itemCount: Int, pageSize: Int): Int? {
    return if (firstItem + pageSize < itemCount) {
      (firstItem + pageSize / pageSize) + 1
    } else {
      null
    }
  }

  private fun createRequest(searchTerm: String, searchCriteria: SearchCriteria, pageKey: Int) = when (searchCriteria) {
    SearchCriteria.AUTHOR -> openLibraryApi.searchByAuthor(searchTerm, pageKey)
    SearchCriteria.TITLE -> openLibraryApi.searchByTitle(searchTerm, pageKey)
    else -> openLibraryApi.search(searchTerm, pageKey)
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Work>) {
    // Ignored, since we only ever append to our initial load
  }

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Work>) {
    networkState.postValue(NetworkState.LOADING)

    if (searchTerm.isEmpty()) {
      networkState.postValue(NetworkState.LOADED)

      callback.onResult(ArrayList<Work>(), null, null)
      return
    }

    // triggered by a refresh, we better execute sync
    try {
      val response = createRequest(
          searchTerm,
          searchCriteria,
          1
      ).execute()

      val data = response.body()
      val items = response.body()?.results ?: ArrayList()

      networkState.postValue(NetworkState.LOADED)

      callback.onResult(
          items,
          null,
          getNextPageKey(data?.start ?: 0, data?.count ?: 0, params.requestedLoadSize)
      )
    } catch (ioException: IOException) {
      ioException.printStackTrace()

      val error = NetworkState.error(ioException.message ?: "unknown error")
      networkState.postValue(error)
    }
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Work>) {
    createRequest(
        searchTerm,
        searchCriteria,
        params.key
    ).enqueue(object : Callback<SearchResponse> {

      override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
        if (response?.isSuccessful == true) {
          val data = response.body()
          val items = response.body()?.results ?: ArrayList()

          callback.onResult(
              items,
              getNextPageKey(data?.start ?: 0, data?.count ?: 0, params.requestedLoadSize)
          )
        } else {
          networkState.postValue(
              NetworkState.error("error code: ${response?.code()}"))
        }
      }

      override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
        networkState.postValue(NetworkState.error(t?.message ?: "unknown err"))
      }
    })
  }
}
