

package com.subhipandey.android.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.mvrx.BaseMvRxFragment
import kotlinx.android.synthetic.main.fragment_watchlist.*


class WatchlistFragment : BaseMvRxFragment() {

  private lateinit var movieAdapter: MovieAdapter

  // add ViewModel declaration here

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_watchlist, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    movieAdapter = MovieAdapter(object : MovieAdapter.WatchlistListener {
      override fun addToWatchlist(movieId: Long) {
        // call ViewModel to add movie to watchlist
      }

      override fun removeFromWatchlist(movieId: Long) {
        // call ViewModel to remove movie from watchlist
      }
    })
    watchlist_movies_recyclerview.adapter = movieAdapter
  }

  override fun invalidate() {
    // modify UI
  }


  private fun showLoader() {
    progress_bar.visibility = View.VISIBLE
    empty_watchlist_textview.visibility = View.GONE
    watchlist_movies_recyclerview.visibility = View.GONE
  }

  private fun showWatchlistedMovies(watchlistedMovies: List<MovieModel>) {
    if (watchlistedMovies.isEmpty()) {
      progress_bar.visibility = View.GONE
      empty_watchlist_textview.visibility = View.VISIBLE
      watchlist_movies_recyclerview.visibility = View.GONE
    } else {
      progress_bar.visibility = View.GONE
      empty_watchlist_textview.visibility = View.GONE
      watchlist_movies_recyclerview.visibility = View.VISIBLE

      movieAdapter.setMovies(watchlistedMovies)
    }
  }

  private fun showError() {
    Toast.makeText(requireContext(), "Failed to load watchlist", Toast.LENGTH_SHORT).show()
  }
}