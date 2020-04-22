

package com.subhipandey.android.bookmanstreasure

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import com.subhipandey.android.bookmanstreasure.ui.MainActivityDelegate
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.subhipandey.android.bookmanstreasure.destinations.AuthorDetailsNavigator
import kotlinx.android.synthetic.main.activity_main.view.*

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityDelegate {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)



      val destination = AuthorDetailsNavigator(navHostFragment.childFragmentManager)
      navHostFragment.findNavController().navigatorProvider.addNavigator(destination)

      val inflater = navHostFragment.findNavController().navInflater
      val graph = inflater.inflate(R.navigation.nav_graph)
      navHostFragment.findNavController().graph = graph
      findNavController(this, R.id.navHostFragment).onHandleDeepLink(intent)
  }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        findNavController(this, R.id.navHostFragment).onHandleDeepLink(intent)
    }




    override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun setupNavDrawer(toolbar: Toolbar) {
    val toggle = ActionBarDrawerToggle(
        this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    drawerLayout.navView.setupWithNavController(
            navHostFragment.findNavController())

  }

  override fun enableNavDrawer(enable: Boolean) {
    drawerLayout.isEnabled = enable
  }

  override fun onSupportNavigateUp() =
          findNavController(this, R.id.navHostFragment).navigateUp()
}
