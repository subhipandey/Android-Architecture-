

package com.subhipandey.android.bookmanstreasure.ui.booksearch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.subhipandey.android.bookmanstreasure.R
import com.subhipandey.android.bookmanstreasure.data.SearchCriteria
import com.subhipandey.android.bookmanstreasure.source.NetworkState
import com.subhipandey.android.bookmanstreasure.ui.MainActivityDelegate
import androidx.navigation.fragment.findNavController
import com.subhipandey.android.bookmanstreasure.ui.workdetails.WorkDetailsViewModel
import com.subhipandey.android.bookmanstreasure.util.initToolbar
import kotlinx.android.synthetic.main.fragment_book_search.*

class BookSearchFragment : Fragment() {

  private lateinit var viewModel: BookSearchViewModel

  private lateinit var mainActivityDelegate: MainActivityDelegate

  override fun onAttach(context: Context?) {
    super.onAttach(context)

    try {
      mainActivityDelegate = context as MainActivityDelegate
    } catch (e: ClassCastException) {
      throw ClassCastException()
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_book_search, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(BookSearchViewModel::class.java)

    viewModel.updateSearchCriteria(SearchCriteria.ALL)

    initToolbar(toolbar, R.string.book_search, false)
    mainActivityDelegate.setupNavDrawer(toolbar)
    mainActivityDelegate.enableNavDrawer(true)

    initCriteriaSpinner()
    initAdapter()

    tvSearch.setOnEditorActionListener { textView, actionId, _ ->

      when (actionId) {
        EditorInfo.IME_ACTION_SEARCH -> {
          hideKeyboard(textView)
          viewModel.updateSearchTerm(textView.text.toString())

          true
        }
        EditorInfo.IME_ACTION_DONE -> {
          viewModel.updateSearchTerm(textView.text.toString())
          true
        }
        else -> false
      }
    }
  }



  private fun hideKeyboard(view: View) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

  private fun initCriteriaSpinner() {
    val adapter = ArrayAdapter<String>(
        context,
        R.layout.item_search_criteria,
        SearchCriteria.values().map {
          it.name
        }
    )
    adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

    spnCriteria.adapter = adapter

    spnCriteria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.updateSearchCriteria(SearchCriteria.valueOf(spnCriteria.adapter.getItem(p2) as String))
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }
  }

  private fun initAdapter() {
    val adapter = WorksAdapter(Glide.with(this))

    rvBooks.adapter = adapter
    adapter.itemClickListener = {
        findNavController().navigate(
                R.id.actionBookDetails,
                WorkDetailsViewModel.createArguments(it)
        )



    }



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
}
