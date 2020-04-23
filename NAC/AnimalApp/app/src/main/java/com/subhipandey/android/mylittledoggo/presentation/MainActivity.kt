

package com.subhipandey.android.mylittledoggo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.subhipandey.android.mylittledoggo.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  private val navController by lazy { findNavController(R.id.nav_host_fragment) }
  private val appBarConfiguration by lazy {
    AppBarConfiguration(
            topLevelDestinationIds = setOf(
                    R.id.doggoListFragment,
                    R.id.favoritesFragment
            )
    )
  }



  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)


    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    setupActionBar()
    setupActionBarWithNavController(navController, appBarConfiguration)

    setupBottomNavigationBar()
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController
            .navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  private fun setupActionBar() {

  }

  private fun setupBottomNavigationBar() {
    bottom_navigation.setupWithNavController(navController)


  }
}
