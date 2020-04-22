

package com.subhipandey.android.bookmanstreasure.ui.bookdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.subhipandey.android.bookmanstreasure.R
import com.subhipandey.android.bookmanstreasure.ui.workdetails.AuthorsAdapter
import com.subhipandey.android.bookmanstreasure.util.initToolbar
import androidx.navigation.fragment.findNavController
import com.subhipandey.android.bookmanstreasure.ui.authordetails.AuthorDetailsViewModel

import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : Fragment() {

  private lateinit var viewModel: BookDetailsViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_book_details, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(BookDetailsViewModel::class.java)

    initToolbar(toolbar, 0, true)
    initDetails()

    viewModel.loadArguments(arguments)
  }

  private fun initDetails() {
    viewModel.book.observe(this, Observer {

      if (it?.cover?.medium != null) {
        Glide.with(this)
            .load(it.cover.medium)
            .error(Glide.with(this).load(R.drawable.book_cover_missing))
            .into(ivCover)
      } else {
        Glide.with(this)
            .load(R.drawable.book_cover_missing)
            .into(ivCover)
      }

      toolbar.title = it?.title
      toolbar.subtitle = it?.subtitle

      tvNumberOfPages.text = getString(R.string.number_of_pages, it?.numberOfPages)
      tvPublishedYear.text = getString(R.string.published_year, it?.publishDate)

      val adapter = AuthorsAdapter(it?.authors ?: ArrayList())
      adapter.itemCLickListener = {
        findNavController().navigate(
                R.id.actionShowAuthor,
                AuthorDetailsViewModel.createArguments(it)
        )

      }

      rvAuthors.adapter = adapter
    })
  }
}
