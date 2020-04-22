

package com.subhipandey.android.loveletter.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.subhipandey.android.loveletter.R
import com.subhipandey.android.loveletter.databinding.FragmentCreateLetterBinding
import com.subhipandey.android.loveletter.extension.Event
import com.subhipandey.android.loveletter.extension.hideKeyboard
import com.subhipandey.android.loveletter.viewmodel.LettersViewModel

class CreateLetterFragment : Fragment() {

  private val lettersViewModel: LettersViewModel by navGraphViewModels(R.id.nav_graph)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)

    val binding: FragmentCreateLetterBinding = DataBindingUtil.inflate(
      inflater, R.layout.fragment_create_letter, container, false
    )
    binding.viewModel = lettersViewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    lettersViewModel.toastLiveData.observe(this, Observer<Event<String>> { it ->
      it.getContentIfNotHandled()?.let {
        Toast.makeText(this@CreateLetterFragment.context, it, Toast.LENGTH_LONG).show()
      }
    })
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.create_letter, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_send -> handleSend { lettersViewModel.sendLetterWithDeeplink() }

      R.id.action_push -> handleSend { lettersViewModel.sendPushNotification() }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun handleSend(toSend: () -> Unit) {
    if (lettersViewModel.hasFullProfile()) {
      toSend()
      findNavController().popBackStack(R.id.inboxFragment, false)
      findNavController().navigate(R.id.sentFragment)
    } else {
      findNavController().navigate(R.id.editProfileFragment)
    }
    hideKeyboard()
  }
}