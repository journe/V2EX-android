package com.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_topic_image.photo_view

class TopicImageActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(com.journey.android.v2ex.R.layout.activity_topic_image)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    Glide.with(this)
        .load(intent.extras[IMAGE_URL])
        .into(object : SimpleTarget<Drawable>() {
          override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?
          ) {
            photo_view.setImageDrawable(resource)
          }
        })
  }

  companion object {
    const val IMAGE_URL = "imageUrl"
    fun start(
      imageUrl: String,
      context: Context
    ) {
      val intent = Intent(context, TopicImageActivity::class.java)
      intent.putExtra(IMAGE_URL, imageUrl)
      context.startActivity(intent)
    }
  }
}
