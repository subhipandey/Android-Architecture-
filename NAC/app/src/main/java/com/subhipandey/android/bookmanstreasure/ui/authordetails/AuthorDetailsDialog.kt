

package com.subhipandey.android.bookmanstreasure.ui.authordetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.subhipandey.android.bookmanstreasure.R

class AuthorDetailsDialog : DialogFragment() {
  private lateinit var viewModel: AuthorDetailsViewModel

  private lateinit var webView: WebView

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.dialog_author_details, container, false)

    webView = rootView.findViewById(R.id.webView)
    val yesButton = rootView.findViewById<Button>(R.id.btnOk)

    yesButton.setOnClickListener { dismiss() }

    return rootView
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(AuthorDetailsViewModel::class.java)
    initAuthorWebView()

    viewModel.loadArguments(arguments)
  }

  private fun initAuthorWebView() {
    webView.webViewClient = WebViewClient()
    viewModel.author.observe(this, Observer {
      val url = it?.url
      val authorUrl = if (url?.startsWith(context?.getString(R.string.http_prefix) ?: "") == true) {
        url
      } else {
        context?.getString(R.string.author_page_url, url)
      }

      webView.loadUrl(authorUrl)
    })
  }

  companion object {
    const val TAG = "AuthorDetailsDialog.TAG"
  }
}
