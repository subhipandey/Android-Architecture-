

package com.subhipandey.android.bookmanstreasure.ui.authordetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.subhipandey.android.bookmanstreasure.data.Author

class AuthorDetailsViewModel : ViewModel() {

  companion object {
    private const val AUTHOR_ARGUMENT = "author"

    fun createArguments(autor: Author): Bundle {
      val bundle = Bundle()
      bundle.putSerializable(AUTHOR_ARGUMENT, autor)

      return bundle
    }
  }

  val author: MutableLiveData<Author> = MutableLiveData()

  fun loadArguments(arguments: Bundle?) {
    if (arguments == null) {
      return
    }

    val author: Author? = arguments.get(AUTHOR_ARGUMENT) as Author
    this.author.postValue(author)
  }
}