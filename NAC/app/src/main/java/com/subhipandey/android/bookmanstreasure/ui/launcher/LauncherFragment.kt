
package com.subhipandey.android.bookmanstreasure.ui.launcher

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.NavOptions

import com.subhipandey.android.bookmanstreasure.R

class LauncherFragment : Fragment(), LifecycleObserver {

  private lateinit var viewModel: LauncherViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {


    return inflater.inflate(R.layout.fragment_launcher, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(LauncherViewModel::class.java)

    viewModel.favoriteCount.observe(this, Observer {
      val destination = if (it.hasFavorites()) {
        R.id.actionFavorites
      } else {
        R.id.actionBookSearch
      }

      findNavController().navigate(
              destination,
              null,
              NavOptions.Builder().setPopUpTo(
                      R.id.launcherFragment,
                      true
              ).build()
      )

    })
  }

  private fun Int?.hasFavorites() = this != null && this > 0
}
