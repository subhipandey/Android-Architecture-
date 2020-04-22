

package com.subhipandey.android.bookmanstreasure.source

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.subhipandey.android.bookmanstreasure.api.OpenLibraryApi
import com.subhipandey.android.bookmanstreasure.data.Book
import com.subhipandey.android.bookmanstreasure.data.Work

class BookDataSourceFactory(
    private val openLibraryApi: OpenLibraryApi
) : DataSource.Factory<Int, Book>() {

  val work = MutableLiveData<Work>()

  val sourceLiveData = MutableLiveData<BookDataSource>()

  override fun create(): DataSource<Int, Book> {
    val source = BookDataSource(openLibraryApi, work.value)
    sourceLiveData.postValue(source)
    return source
  }
}