

package com.subhipandey.android.loveletter.agreement

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import com.subhipandey.android.loveletter.R
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

class PrivacyPolicyFragment : Fragment(R.layout.fragment_privacy_policy) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val text =
      "<p>Your privacy is important to us. It is subhipandey.com's policy to respect your privacy regarding any information we may collect from you through our app, Love Letter.</p>\n" +
          "<p>We only ask for personal information when we truly need it to provide a service to you. We collect it by fair and lawful means, with your knowledge and consent. We also let you know why we’re collecting it and how it will be used.</p>\n" +
          "<p>We only retain collected information for as long as necessary to provide you with your requested service. What data we store, we’ll protect within commercially acceptable means to prevent loss and theft, as well as unauthorised access, disclosure, copying, use or modification.</p>\n" +
          "<p>We don’t share any personally identifying information publicly or with third-parties, except when required to by law.</p>\n" +
          "<p>Our app may link to external sites that are not operated by us. Please be aware that we have no control over the content and practices of these sites, and cannot accept responsibility or liability for their respective privacy policies.</p>\n" +
          "<p>You are free to refuse our request for your personal information, with the understanding that we may be unable to provide you with some of your desired services.</p>\n" +
          "<p>Your continued use of our website will be regarded as acceptance of our practices around privacy and personal information. If you have any questions about how we handle user data and personal information, feel free to contact us.</p>\n" +
          "<p>This policy is effective as of 1 July 2019.</p>\n"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
      @Suppress("DEPRECATION")
      textView.text = Html.fromHtml(text)
    }
  }
}

