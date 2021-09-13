package com.journey.android.v2ex.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.eer.mmmh.base.mvvm.v.FrameView

/**
 * Activity基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseFrameActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameStatusActivity(),
  FrameView<VB> {

  protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
    BindingReflex.reflexViewBinding(javaClass, layoutInflater)
  }

  protected abstract val mViewModel: VM

  /**
   * 基础UI状态管理工具 保存了是否重建的状态信息
   */
  private lateinit var mStatusHelper: BaseFrameViewStatusHelperImp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(mBinding.root)

    setStatusBar()
    mBinding.initView()
    initObserve()
    initRequestData()
  }

  /**
   * 设置状态栏
   * 子类需要自定义时重写该方法即可
   * @return Unit
   */
  open fun setStatusBar() {}

  override fun isRecreate(): Boolean = mStatusHelper.isRecreate

  override fun onRegisterStatusHelper(): ViewStatusHelper? {
    mStatusHelper = BaseFrameViewStatusHelperImp(super.onRegisterStatusHelper())
    return mStatusHelper
  }

}