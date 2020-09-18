package com.journey.android.v2ex

import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import androidx.lifecycle.AndroidViewModel
import com.journey.android.v2ex.libs.extension.launch

/**
 * Created by journey on 2020/9/17.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
  private var wifiManager: WifiManager =
    application.getSystemService(Context.WIFI_SERVICE) as WifiManager
  fun getConfiguredNetworks() = wifiManager.configuredNetworks

  fun loadTopicDetail(){
    launch({

    })
  }
}