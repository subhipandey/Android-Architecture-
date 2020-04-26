

package com.subhipandey.android.watchlist

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WatchlistRepository {

  private val movies = mutableListOf<MovieModel>()

  fun getWatchlistedMovies() = Observable.fromCallable<List<MovieModel>> {
    Thread.sleep(3000)
    movies.addAll(listOf(
        MovieModel(
            1234,
            "Fight Club",
            "https://image.tmdb.org/t/p/w500/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg",
            false
        ),
        MovieModel(
            1235,
            "Ad Astra",
            "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
            false
        ),
        MovieModel(
            1236,
            "Joker",
            "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            false
        ),
        MovieModel(
            1237,
            "Star Wars: The Rise of Skywalker",
            "https://image.tmdb.org/t/p/w500/db32LaOibwEliAmSL2jjDF6oDdj.jpg",
            false
        ),
        MovieModel(
            1238,
            "Jumanji: The Next Level",
            "https://image.tmdb.org/t/p/w500//jyw8VKYEiM1UDzPB7NsisUgBeJ8.jpg",
            false
        ),
        MovieModel(
            1239,
            "Ip Man 4: The Finale",
            "https://image.tmdb.org/t/p/w500/yJdeWaVXa2se9agI6B4mQunVYkB.jpg",
            false
        ),
        MovieModel(
            1210,
            "Frozen II",
            "https://image.tmdb.org/t/p/w500/pjeMs3yqRmFL3giJy4PMXWZTTPa.jpg",
            false
        ),
        MovieModel(
            1211,
            "Terminator: Dark Fate",
            "https://image.tmdb.org/t/p/w500/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg",
            false
        ),
        MovieModel(
            1212,
            "Once Upon a Time… in Hollywood",
            "https://image.tmdb.org/t/p/w500/8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg",
            false
        ),
        MovieModel(
            1213,
            "Maleficent: Mistress of Evil",
            "https://image.tmdb.org/t/p/w500/vloNTScJ3w7jwNwtNGoG8DbTThv.jpg",
            false
        )
    ))
    movies

  }.subscribeOn(Schedulers.io())

  // add method to watchlist a movie

  // add method to remove a movie from watchlist

}