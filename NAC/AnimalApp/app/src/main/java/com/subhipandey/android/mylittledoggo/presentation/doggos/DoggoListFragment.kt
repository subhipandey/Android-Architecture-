

package com.subhipandey.android.mylittledoggo.presentation.doggos

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.subhipandey.android.mylittledoggo.R
import com.subhipandey.android.mylittledoggo.presentation.DoggosAdapter
import com.subhipandey.android.mylittledoggo.presentation.extensions.load
import kotlinx.android.synthetic.main.fragment_doggo.*
import kotlinx.android.synthetic.main.fragment_doggos_list.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DoggoListFragment : Fragment() {

  private val viewModel: DoggoListViewModel by viewModels { DoggoListViewModelFactory }
  private var isLoadingMoreItems = false

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_doggos_list, container, false)
    val doggosAdapter = createAdapter()

    setupRecyclerView(view, doggosAdapter)
    observeViewModel(doggosAdapter)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setExitToFullScreenTransition()
    setReturnFromFullScreenTransition()
  }

  private fun createAdapter(): DoggosAdapter {
    return DoggosAdapter { view, doggo ->
      val extraInfoForSharedElement = FragmentNavigatorExtras(
              view to doggo.picture
      )
      val toDoggoFragment =
              DoggoListFragmentDirections.toDoggoFragment(doggo.picture, doggo.isFavourite)

      navigate(toDoggoFragment, extraInfoForSharedElement)
    }
  }

  private fun setupRecyclerView(view: View, doggosAdapter: DoggosAdapter) {
    view.recycler_view_doggos.run {
      adapter = doggosAdapter

      setHasFixedSize(true)
      addOnScrollListener(createInfiniteScrollListener(layoutManager as GridLayoutManager))

      postponeEnterTransition()
      viewTreeObserver.addOnPreDrawListener {
        startPostponedEnterTransition()
        true
      }
    }
  }

  private fun createInfiniteScrollListener(
          gridLayoutManager: GridLayoutManager
  ): RecyclerView.OnScrollListener {
    return object : InfiniteScrollListener(gridLayoutManager, DoggoListViewModel.PAGE_SIZE) {
      override fun loadMoreItems() {
        isLoadingMoreItems = true
        viewModel.getMoreDoggos(DoggoListViewModel.PAGE_SIZE)
      }

      override fun isLoading(): Boolean = isLoadingMoreItems
    }
  }

  private fun observeViewModel(doggosAdapter: DoggosAdapter) {
    viewModel.doggoList.observe(viewLifecycleOwner) {
      doggosAdapter.submitList(it)
      isLoadingMoreItems = false
    }
  }

  private fun setExitToFullScreenTransition() {
    exitTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.doggo_list_exit_transition)
  }

  private fun setReturnFromFullScreenTransition() {
    reenterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.doggo_list_return_transition)
  }

  private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination, extraInfo) }
  }
}