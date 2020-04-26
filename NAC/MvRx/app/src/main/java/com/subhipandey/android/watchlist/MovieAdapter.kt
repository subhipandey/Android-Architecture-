

package com.subhipandey.android.watchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val wathclistListener: WatchlistListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  private val movies = mutableListOf<MovieModel>()

  fun setMovies(movies: List<MovieModel>) {
    this.movies.clear()
    this.movies.addAll(movies)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.movie_viewholder_layout, parent, false)
    return MovieViewHolder(view)
  }

  override fun getItemCount() = movies.size

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = movies[position]

    Glide
        .with(holder.itemView)
        .load(movie.posterLink)
        .centerCrop()
        .into(holder.posterImageView)

    holder.movieNameTextView.text = movie.name
    if (movie.isWatchlisted) {
      holder.watchlistButton.setImageResource(R.drawable.ic_remove_from_watchlist)
    } else {
      holder.watchlistButton.setImageResource(R.drawable.ic_add_to_watchlist)
    }
    holder.watchlistButton.setOnClickListener {
      if (movie.isWatchlisted) {
        wathclistListener.removeFromWatchlist(movie.id)
      } else {
        wathclistListener.addToWatchlist(movie.id)
      }
    }
  }


  inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val posterImageView: ImageView = itemView.findViewById(R.id.movie_poster_imageview)
    val movieNameTextView: TextView = itemView.findViewById(R.id.movie_name_textview)
    val watchlistButton: ImageView = itemView.findViewById(R.id.watchlist_button)
  }

  interface WatchlistListener {

    fun addToWatchlist(movieId: Long)

    fun removeFromWatchlist(movieId: Long)
  }
}