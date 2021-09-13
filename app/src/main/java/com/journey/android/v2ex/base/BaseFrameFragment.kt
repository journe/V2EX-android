package com.journey.android.v2ex.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.eer.mmmh.base.mvvm.v.FrameView

/**
 * Fragment基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseFrameFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameStatusFragment(),
    FrameView<VB> {

    /**
     * 私有的 ViewBinding 此写法来自 Google Android 官方
     */
    private var _binding: VB? = null

    protected val mBinding get() = _binding!!

    protected abstract val mViewModel: VM

    /**
     * 基础UI状态管理工具
     */
    private lateinit var mBaseStatusHelper: BaseFrameViewStatusHelperImp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.initView()
        initObserve()
        initRequestData()
    }

    override fun isRecreate(): Boolean = mBaseStatusHelper.isRecreate

    override fun onRegisterStatusHelper(): ViewStatusHelper? {
        mBaseStatusHelper = BaseFrameViewStatusHelperImp(super.onRegisterStatusHelper())
        return mBaseStatusHelper
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}