package com.journey.android.v2ex.module.topic.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.base.EmptyViewModel
import com.journey.android.v2ex.databinding.FragmentTopicImageBinding

class TopicImageFragment : BaseFragment<FragmentTopicImageBinding, EmptyViewModel>() {

  override val mViewModel: EmptyViewModel by viewModels()

  override fun FragmentTopicImageBinding.initView() {
    val safeArgs: TopicImageFragmentArgs by navArgs()
    mBinding.photoView.load(safeArgs.imageUrl)
  }

  override fun initObserve() {
  }

  override fun initRequestData() {
  }

}
