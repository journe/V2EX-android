package com.journey.android.v2ex.base

import androidx.viewbinding.ViewBinding

/**
 * Created by journey on 2018/1/26.
 */

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameActivity<VB, VM>() {

  /**
   * 设置状态栏
   * 子类需要自定义时重写该方法即可
   * @return Unit
   */
  override fun setStatusBar() {
//    val themeColor = ContextCompat.getColor(this, R.color.common_theme)
//    StatusBarUtil.setColor(this, themeColor, 0)
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onDestroy() {
    super.onDestroy()
  }
}
