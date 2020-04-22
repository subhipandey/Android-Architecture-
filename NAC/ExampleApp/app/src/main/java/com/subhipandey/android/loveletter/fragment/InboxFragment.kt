

package com.subhipandey.android.loveletter.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.subhipandey.android.loveletter.R
import com.subhipandey.android.loveletter.adapter.LetterAdapter
import com.subhipandey.android.loveletter.model.FragmentType
import com.subhipandey.android.loveletter.model.Letter
import com.subhipandey.android.loveletter.viewmodel.LettersViewModel
import kotlinx.android.synthetic.main.fragment_inbox.*

class InboxFragment : Fragment(R.layout.fragment_inbox) {

  private val lettersViewModel: LettersViewModel by navGraphViewModels(R.id.nav_graph)


  private val adapter by lazy { LetterAdapter(FragmentType.INBOX) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter.setItemClickListener {
      findNavController().navigate(InboxFragmentDirections.presentLetter(Gson().toJson(it)))

    }
    adapter.setItemDeleteListener {
      lettersViewModel?.deleteLetter(it, FragmentType.INBOX)
    }
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = adapter

    lettersViewModel?.inboxLettersLiveData?.observe(this, Observer { listItems: List<Letter> ->
      adapter.update(listItems)
    })
    lettersViewModel?.loadInboxLetters()
  }
}

