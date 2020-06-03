

package com.subhipandey.stashpile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (savedInstanceState == null) {
      openProfile()
    }
  }

  private fun openProfile() {
    supportFragmentManager.beginTransaction()
        .add(R.id.container, ProfileFragment())
        .commit()
  }
}