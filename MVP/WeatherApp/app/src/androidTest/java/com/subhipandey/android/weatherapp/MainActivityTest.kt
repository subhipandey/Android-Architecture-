

package com.subhipandey.android.weatherapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {


  @get:Rule
  var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

  @Test
  fun weatherIconIsLoadedInImageView() {
    onView(withId(R.id.imageView)).check(matches(DrawableMatcher(listOf(R.drawable.ic_umbrella, R.drawable.ic_sun))))
  }
}