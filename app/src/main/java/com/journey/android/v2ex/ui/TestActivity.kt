package com.journey.android.v2ex.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.journey.android.v2ex.R
import kotlinx.android.synthetic.main.activity_test.button0
import kotlinx.android.synthetic.main.activity_test.button1
import kotlinx.android.synthetic.main.activity_test.button2

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_test)
    button0.setOnClickListener {
      TopicDetailActivity.start(550323, this)
    }
    button1.setOnClickListener {
      TopicDetailJsActivity.start(550323, this)
    }
    button2.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }
  }
}
