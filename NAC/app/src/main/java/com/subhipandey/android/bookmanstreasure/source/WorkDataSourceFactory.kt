

package com.subhipandey.android.bookmanstreasure.source

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.subhipandey.android.bookmanstreasure.api.OpenLibraryApi
import com.subhipandey.android.bookmanstreasure.data.SearchCriteria
import com.subhipandey.android.bookmanstreasure.data.Work

class WorkDataSourceFactory(
    private val openLibraryApi: OpenLibraryApi
) : DataSource.Factory<Int, Work>() {

  val searchTerm = MutableLiveData<String>()

  val searchCriteria = MutableLiveData<SearchCriteria>()

  val sourceLiveData = MutableLiveData<WorkDataSource>()

  override fun create(): DataSource<Int, Work> {
    val source = WorkDataSource(
        openLibraryApi,
        searchTerm.value ?: "",
        searchCriteria.value ?: SearchCriteria.ALL
    )
    sourceLiveData.postValue(source)
    return source
  }
}
