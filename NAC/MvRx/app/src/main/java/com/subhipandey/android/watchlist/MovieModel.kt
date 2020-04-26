
package com.subhipandey.android.watchlist

data class MovieModel(
    val id: Long,
    val name: String,
    val posterLink: String,
    val isWatchlisted: Boolean
)