package com.journey.android.v2ex.router

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import com.journey.android.v2ex.module.topic.detail.TopicDetailFragmentDirections

object Router {
  private lateinit var uri: Uri
  lateinit var navController: NavController

  fun init(
    nav: NavController
  ) {
    navController = nav
  }

  fun navigate(
    action: NavDirections,
    fragmentNavigatorExtras: FragmentNavigator.Extras? = null
  ) {
    if (fragmentNavigatorExtras != null) {
      navController.navigate(action, fragmentNavigatorExtras)
    } else {
      navController.navigate(action)
    }
  }

  fun imageDetail(url: String) {
    val action =
      TopicDetailFragmentDirections.topicDetailImage(url)
    navController.navigate(action)
  }
}