package com.journey.android.v2ex.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.journey.android.v2ex.R
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
    Glide.with(this)
        .load(safeArgs.imageUrl)
        .into(object : SimpleTarget<Drawable>() {
          override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?
          ) {
            photo_view.setImageDrawable(resource)
          }
        })
  }

}
