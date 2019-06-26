package com.journey.android.v2ex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.journey.android.v2ex.R
import kotlinx.android.synthetic.main.activity_test.button0
import kotlinx.android.synthetic.main.activity_test.button1
import kotlinx.android.synthetic.main.activity_test.button2
import kotlinx.android.synthetic.main.activity_test.button3
import kotlinx.android.synthetic.main.activity_test.button4

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_test)
    button0.setOnClickListener {
//      TopicDetailActivity.start(550419, this)
    }
    button1.setOnClickListener {
      //DIY 24 寸的 4K 显示器
//      TopicDetailJsActivity.start(567460, this)
      //一个完全靠抄的项目-附言-评论数量
//      TopicDetailJsActivity.start(550323, this)
//      TopicDetailJsActivity.start(553960, this)
    }
    button2.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }
    button3.setOnClickListener {
      //      startActivity(Intent(this, LoginActivity::class.java))
    }
    button4.setOnClickListener {
      startActivity(Intent(this, BalanceActivity::class.java))
    }
  }
}
