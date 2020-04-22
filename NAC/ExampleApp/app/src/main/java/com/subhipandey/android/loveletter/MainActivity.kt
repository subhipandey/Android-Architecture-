

package com.subhipandey.android.loveletter

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.subhipandey.android.loveletter.databinding.ActivityMainBinding
import com.subhipandey.android.loveletter.databinding.NavHeaderMainBinding
import com.subhipandey.android.loveletter.viewmodel.LettersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  private val navController by lazy { findNavController(R.id.nav_host_fragment) }
  private val appBarConfiguration by lazy {
    AppBarConfiguration(
      setOf(
        R.id.sentFragment,
        R.id.inboxFragment
      ), drawerLayout
    )
  } //2




  private var lettersViewModel: LettersViewModel? = null
  private lateinit var headerBinding: NavHeaderMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupDataBinding()
    setSupportActionBar(toolbar)
    setupNavigation()
    setupViewModel()
    setupViews()
  }

  override fun onDestroy() {
    lettersViewModel?.apply { closeDb() }
    super.onDestroy()
  }

  private fun setupDataBinding() {
    val activityMainBinding =
      DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    headerBinding = DataBindingUtil.inflate(
      layoutInflater, R.layout.nav_header_main, activityMainBinding.navView, false
    )
    headerBinding.ivEdit.setOnClickListener {
      navController.navigate(R.id.editProfileFragment)


      drawerLayout.closeDrawer(GravityCompat.START)
    }
    activityMainBinding.navView.addHeaderView(headerBinding.root)
  }

  private fun setupNavigation() {
    NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)  //1

    NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)  //2

    navController.addOnDestinationChangedListener { _, destination, _ ->  //3
      if (destination.id in arrayOf(
          R.id.createLetterFragment,
          R.id.presentationFragment,
          R.id.editProfileFragment
        )
      ) {
        fab.hide()
      } else {
        fab.show()
      }

      if (destination.id == R.id.presentationFragment) {
        toolbar.visibility = View.GONE
      } else {
        toolbar.visibility = View.VISIBLE
      }
    }


  }

  private fun setupViewModel() {
    try {
      val viewModelProvider = ViewModelProvider(
        navController.getViewModelStoreOwner(R.id.nav_graph),
        ViewModelProvider.AndroidViewModelFactory(application)
      )
      lettersViewModel = viewModelProvider.get(LettersViewModel::class.java)
      headerBinding.viewModel = lettersViewModel
      lettersViewModel?.loadProfile()
    } catch (e: IllegalArgumentException) {
      e.printStackTrace()
    }


  }

  private fun setupViews() {
    navView.setNavigationItemSelectedListener(this)

    fab.setOnClickListener {
      navController.navigate(R.id.createLetterFragment)


    }
  }

  override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {

      R.id.nav_inbox -> {
        navController.popBackStack(R.id.inboxFragment, false)
      }

      R.id.nav_sent -> {
        navController.navigate(R.id.sentFragment)
      }

      R.id.nav_privacy_policy -> {
        navController.navigate(Uri.parse("loveletter://agreement/privacy-policy"))

      }

      R.id.nav_terms_of_service -> {
        navController.navigate(Uri.parse("loveletter://agreement/terms-of-service"))

      }
    }
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}
