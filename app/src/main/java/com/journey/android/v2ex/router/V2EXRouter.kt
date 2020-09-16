package com.journey.android.v2ex.router

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.journey.android.v2ex.base.BaseActivity
import com.journey.android.v2ex.module.topic.MainFragmentDirections

object V2EXRouter {
  private lateinit var uri: Uri
  lateinit var context: AppCompatActivity
  lateinit var navController: NavController

  fun init(
    activity: BaseActivity,
    nav: NavController
  ) {
    context = activity
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
}