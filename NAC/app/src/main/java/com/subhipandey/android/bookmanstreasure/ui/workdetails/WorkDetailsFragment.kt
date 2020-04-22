
package com.subhipandey.android.bookmanstreasure.ui.workdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.bumptech.glide.Glide
import com.subhipandey.android.bookmanstreasure.R
import com.subhipandey.android.bookmanstreasure.data.Author
import com.subhipandey.android.bookmanstreasure.data.Work
import com.subhipandey.android.bookmanstreasure.source.NetworkState
import com.subhipandey.android.bookmanstreasure.util.CoverSize
import com.subhipandey.android.bookmanstreasure.util.initToolbar
import com.subhipandey.android.bookmanstreasure.util.loadCover
import androidx.navigation.fragment.findNavController
import com.subhipandey.android.bookmanstreasure.ui.authordetails.AuthorDetailsViewModel
import com.subhipandey.android.bookmanstreasure.ui.bookdetails.BookDetailsViewModel
import kotlinx.android.synthetic.main.fragment_work_details.*

class WorkDetailsFragment : Fragment() {

  private lateinit var viewModel: WorkDetailsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_work_details, container, false)
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.work_details, menu)

    // Make menu items invisible until details are loaded.
    menu?.findItem(R.id.menuAddFavorite)?.isVisible = false
    menu?.findItem(R.id.menuRemoveFavorite)?.isVisible = false
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      R.id.menuAddFavorite -> viewModel.addAsFavorite()
      R.id.menuRemoveFavorite -> viewModel.removeFromFavorites()
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(WorkDetailsViewModel::class.java)

    initToolbar(toolbar, 0, true)
    initDetails()
    initEditionsAdapter()

    toolbar.postDelayed({ viewModel.loadArguments(arguments) }, 100)
  }

  private fun initDetails() {
    viewModel.work.observe(this, Observer { work ->

      if (work?.coverId != null) {
        Glide.with(this)
                .loadCover(work.coverId, CoverSize.M)
                .error(Glide.with(this).load(R.drawable.book_cover_missing))
                .into(ivCover)
      } else {
        Glide.with(this)
                .load(R.drawable.book_cover_missing)
                .into(ivCover)
      }

      toolbar.title = work?.title
      toolbar.subtitle = work?.subtitle

      viewModel.favorite.observe(this@WorkDetailsFragment, Observer { favorite ->
        if (favorite != null) {
          toolbar.menu.findItem(R.id.menuAddFavorite)?.isVisible = false
          toolbar.menu.findItem(R.id.menuRemoveFavorite)?.isVisible = true
        } else {
          toolbar.menu.findItem(R.id.menuAddFavorite)?.isVisible = true
          toolbar.menu.findItem(R.id.menuRemoveFavorite)?.isVisible = false
        }
      })

      val adapter = AuthorsAdapter(getAuthors(work))
      adapter.itemCLickListener = {
        findNavController().navigate(
                R.id.actionShowAuthor,
                AuthorDetailsViewModel.createArguments(it)
        )
      }

      rvAuthors.adapter = adapter

      val numberOfEditions = work?.editionIsbns?.size ?: 0

      tvEditions.text = resources.getQuantityString(R.plurals.editions_available,
              numberOfEditions, numberOfEditions)
    })
  }

  private fun initEditionsAdapter() {
    val adapter = BooksAdapter(Glide.with(this))

    rvEditions.adapter = adapter
    adapter.itemClickListener = {
      findNavController().navigate(
              R.id.actionShowEdition,
              BookDetailsViewModel.createArguments(it)
      )    }

    viewModel.data.observe(this, Observer {
      adapter.submitList(it)
    })

    viewModel.networkState.observe(this, Observer {
      progressBar.visibility = when (it) {
        NetworkState.LOADING -> View.VISIBLE
        else -> View.GONE
      }
    })
  }

  private fun getAuthors(it: Work?): List<Author> {
    val authors = ArrayList<Author>()

    if (it?.authorName?.size != null) {
      for (i in 0 until it.authorName.size) {
        authors.add(
                Author(
                        it.authorName[i],
                        it.authorKey[i]
                )
        )
      }
    }

    return authors
  }
}
