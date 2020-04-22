

package com.subhipandey.android.loveletter.agreement

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import com.subhipandey.android.loveletter.R
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

class TermsOfServiceFragment : Fragment(R.layout.fragment_terms_of_service) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val text = "<h3>1. Terms</h3>\n" +
        "<p>By accessing our app, Love Letter, you are agreeing to be bound by these terms of service, all applicable laws and regulations, and agree that you are responsible for compliance with any applicable local laws. If you do not agree with any of these terms, you are prohibited from using or accessing Love Letter. The materials contained in Love Letter are protected by applicable copyright and trademark law.</p>\n" +
        "<h3>2. Use License</h3>\n" +
        "<ol type=\"a\">\n" +
        "   <li>Permission is granted to temporarily download one copy of Love Letter per device for personal, non-commercial transitory viewing only. This is the grant of a license, not a transfer of title, and under this license you may not:\n" +
        "   <ol type=\"i\">\n" +
        "       <li>modify or copy the materials;</li>\n" +
        "       <li>use the materials for any commercial purpose, or for any public display (commercial or non-commercial);</li>\n" +
        "       <li>attempt to decompile or reverse engineer any software contained in Love Letter;</li>\n" +
        "       <li>remove any copyright or other proprietary notations from the materials; or</li>\n" +
        "       <li>transfer the materials to another person or \"mirror\" the materials on any other server.</li>\n" +
        "   </ol>\n" +
        "    </li>\n" +
        "   <li>This license shall automatically terminate if you violate any of these restrictions and may be terminated by subhipandey.com at any time. Upon terminating your viewing of these materials or upon the termination of this license, you must destroy any downloaded materials in your possession whether in electronic or printed format.</li>\n" +
        "</ol>\n" +
        "<h3>3. Disclaimer</h3>\n" +
        "<ol type=\"a\">\n" +
        "   <li>The materials within Love Letter are provided on an 'as is' basis. subhipandey.com makes no warranties, expressed or implied, and hereby disclaims and negates all other warranties including, without limitation, implied warranties or conditions of merchantability, fitness for a particular purpose, or non-infringement of intellectual property or other violation of rights.</li>\n" +
        "   <li>Further, subhipandey.com does not warrant or make any representations concerning the accuracy, likely results, or reliability of the use of the materials on its website or otherwise relating to such materials or on any sites linked to Love Letter.</li>\n" +
        "</ol>\n" +
        "<h3>4. Limitations</h3>\n" +
        "<p>In no event shall subhipandey.com or its suppliers be liable for any damages (including, without limitation, damages for loss of data or profit, or due to business interruption) arising out of the use or inability to use Love Letter, even if subhipandey.com or a subhipandey.com authorized representative has been notified orally or in writing of the possibility of such damage. Because some jurisdictions do not allow limitations on implied warranties, or limitations of liability for consequential or incidental damages, these limitations may not apply to you.</p>\n" +
        "<h3>5. Accuracy of materials</h3>\n" +
        "<p>The materials appearing in Love Letter could include technical, typographical, or photographic errors. subhipandey.com does not warrant that any of the materials on Love Letter are accurate, complete or current. subhipandey.com may make changes to the materials contained in Love Letter at any time without notice. However subhipandey.com does not make any commitment to update the materials.</p>\n" +
        "<h3>6. Links</h3>\n" +
        "<p>subhipandey.com has not reviewed all of the sites linked to its app and is not responsible for the contents of any such linked site. The inclusion of any link does not imply endorsement by subhipandey.com of the site. Use of any such linked website is at the user's own risk.</p>\n" +
        "<h3>7. Modifications</h3>\n" +
        "<p>subhipandey.com may revise these terms of service for its app at any time without notice. By using Love Letter you are agreeing to be bound by the then current version of these terms of service.</p>\n" +
        "<h3>8. Governing Law</h3>\n" +
        "<p>These terms and conditions are governed by and construed in accordance with the laws of USA and you irrevocably submit to the exclusive jurisdiction of the courts in that State or location.</p>\n"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
      @Suppress("DEPRECATION")
      textView.text = Html.fromHtml(text)
    }
  }
}

