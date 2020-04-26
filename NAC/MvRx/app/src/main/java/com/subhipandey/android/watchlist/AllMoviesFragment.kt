

package com.subhipandey.android.watchlist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.*
import com.airbnb.mvrx.R

import kotlinx.android.synthetic.main.fragment_all_movies.*

class AllMoviesFragment : BaseMvRxFragment() {

  private lateinit var movieAdapter: MovieAdapter

  // add ViewModel declaration here
  private val watchlistViewModel: WatchlistViewModel by activityViewModel()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_all_movies, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    movieAdapter = MovieAdapter(object : MovieAdapter.WatchlistListener {
      override fun addToWatchlist(movieId: Long) {
        // call ViewModel to add movie to watchlist
        watchlistViewModel.watchlistMovie(movieId)
      }

      override fun removeFromWatchlist(movieId: Long) {
        // call ViewModel to remove movie from watchlist
        watchlistViewModel.removeMovieFromWatchlist(movieId)
      }
    })
    all_movies_recyclerview.adapter = movieAdapter
    watchlistViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
      Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    })
  }

  override fun invalidate() {
    withState(watchlistViewModel) { state ->
      when (state.movies) {
        is Loading -> {
          progress_bar.visibility = View.VISIBLE
          all_movies_recyclerview.visibility = View.GONE
        }
        is Success -> {
          progress_bar.visibility = View.GONE
          all_movies_recyclerview.visibility = View.VISIBLE
          movieAdapter.setMovies(state.movies.invoke())
        }
        is Fail -> {
          Toast.makeText(requireContext(), "Failed to load all movies", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.watchlist, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.watchlist) {
      val watchlistFragment = WatchlistFragment()
      val transaction = requireFragmentManager().beginTransaction()
      transaction.replace(R.id.fragment_container, watchlistFragment)
      transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
      transaction.addToBackStack(null)
      transaction.commit()
    }
    return super.onOptionsItemSelected(item)
  }
}