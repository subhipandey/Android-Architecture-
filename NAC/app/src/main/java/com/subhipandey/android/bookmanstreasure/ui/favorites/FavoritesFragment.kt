

package com.subhipandey.android.bookmanstreasure.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.subhipandey.android.bookmanstreasure.R
import com.subhipandey.android.bookmanstreasure.ui.MainActivityDelegate
import com.subhipandey.android.bookmanstreasure.ui.booksearch.WorksAdapter
import com.subhipandey.android.bookmanstreasure.util.initToolbar
import androidx.navigation.fragment.findNavController
import com.subhipandey.android.bookmanstreasure.ui.workdetails.WorkDetailsViewModel

import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

  private lateinit var viewModel: FavoritesViewModel

  private lateinit var mainActivityDelegate: MainActivityDelegate

  override fun onAttach(context: Context?) {
    super.onAttach(context)

    try {
      mainActivityDelegate = context as MainActivityDelegate
    } catch (e: ClassCastException) {
      throw ClassCastException("Host activity must implement MainActivity")
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_favorites, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

    initToolbar(toolbar, R.string.favorites, false)
    mainActivityDelegate.setupNavDrawer(toolbar)
    mainActivityDelegate.enableNavDrawer(true)

    initAdapter()
  }

  override fun onResume() {
    super.onResume()
    viewModel.refreshList()
  }

  private fun initAdapter() {
    val adapter = WorksAdapter(Glide.with(this)) {
      findNavController().navigate(
              R.id.actionBookDetails,
              WorkDetailsViewModel.createArguments(it)
      )

    }

    viewModel.data.observe(this, Observer {
      adapter.submitList(it)
    })

    rvWorks.adapter = adapter
  }
}
