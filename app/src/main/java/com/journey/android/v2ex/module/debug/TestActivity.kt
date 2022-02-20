package com.journey.android.v2ex.module.debug

import android.content.Intent
import androidx.activity.viewModels
import com.journey.android.v2ex.MainActivity
import com.journey.android.v2ex.base.BaseActivity
import com.journey.android.v2ex.databinding.ActivityTestBinding
import com.journey.android.v2ex.module.BalanceActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>() {

	override val mViewModel: TestViewModel by viewModels()

	override fun ActivityTestBinding.initView() {
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
			startActivity(Intent(this@TestActivity, MainActivity::class.java))
		}
		getproflie.setOnClickListener {
			//      startActivity(Intent(this, LoginActivity::class.java))
			mViewModel.getProfile()
		}
		button4.setOnClickListener {
			startActivity(Intent(this@TestActivity, BalanceActivity::class.java))
		}
		allnodes.setOnClickListener {
			mViewModel.getAllNodes()
		}
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}
}
