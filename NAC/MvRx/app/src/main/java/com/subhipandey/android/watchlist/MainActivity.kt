

package com.subhipandey.android.watchlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val allMoviesFragment = AllMoviesFragment()
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_container, allMoviesFragment)
    transaction.commit()
  }
}
