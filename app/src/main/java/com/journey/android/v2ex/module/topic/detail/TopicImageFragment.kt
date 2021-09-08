package com.journey.android.v2ex.module.topic.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_topic_image.photo_view

class TopicImageFragment : BaseFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_topic_image, container, false)

  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    val safeArgs: TopicImageFragmentArgs by navArgs()
    photo_view.load(safeArgs.imageUrl)
  }

}
