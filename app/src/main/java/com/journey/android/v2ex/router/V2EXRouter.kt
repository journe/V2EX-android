package com.journey.android.v2ex.router

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import com.journey.android.v2ex.base.BaseActivity

object V2EXRouter {
  private lateinit var uri: Uri
  private lateinit var baseActivity: BaseActivity
  lateinit var navController: NavController

  fun init(
    activity: BaseActivity,
    nav: NavController
  ) {
    baseActivity = activity
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