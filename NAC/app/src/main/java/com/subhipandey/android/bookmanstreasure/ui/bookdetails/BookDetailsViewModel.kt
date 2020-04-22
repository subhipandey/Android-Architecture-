

package com.subhipandey.android.bookmanstreasure.ui.bookdetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.subhipandey.android.bookmanstreasure.data.Book

class BookDetailsViewModel : ViewModel() {

  companion object {
    private const val BOOK_ARGUMENT = "book"

    fun createArguments(book: Book): Bundle {
      val bundle = Bundle()
      bundle.putSerializable(BOOK_ARGUMENT, book)

      return bundle
    }
  }

  val book: MutableLiveData<Book> = MutableLiveData()

  fun loadArguments(arguments: Bundle?) {
    if (arguments == null) {
      return
    }

    val book: Book? = arguments.get(BOOK_ARGUMENT) as Book?
    this.book.postValue(book)
  }
}
