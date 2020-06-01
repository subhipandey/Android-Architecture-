package com.subhipandey.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.subhipandey.newsapp.R
import com.subhipandey.newsapp.ui.NewsActivity
import com.subhipandey.newsapp.ui.NewsViewModel

class SavedNewsFragment :Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }
}