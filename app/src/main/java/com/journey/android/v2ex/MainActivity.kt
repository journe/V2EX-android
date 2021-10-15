package com.journey.android.v2ex

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.journey.android.v2ex.base.BaseActivity
import com.journey.android.v2ex.databinding.ActivityMainBinding
import com.journey.android.v2ex.libs.extension.gone
import com.journey.android.v2ex.libs.extension.visible
import com.journey.android.v2ex.libs.transition.EdgeToEdge
import com.journey.android.v2ex.router.Router
import com.journey.android.v2ex.utils.UserState
import com.zzhoujay.richtext.RichText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

	private lateinit var appBarConfiguration: AppBarConfiguration

	override val mViewModel: MainViewModel by viewModels()

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
	}

	override fun onDestroy() {
		super.onDestroy()
		RichText.recycle()
		mViewModel.deleteCache()
	}

	override fun ActivityMainBinding.initView() {
		setSupportActionBar(mBinding.mainToolbar)

		EdgeToEdge.setUpRoot(findViewById(R.id.drawer_layout))
		EdgeToEdge.setUpAppBar(mBinding.appBar, mBinding.mainToolbar)
		EdgeToEdge.setUpScrollingContent(findViewById(R.id.main_constraint))

		val host: NavHostFragment = supportFragmentManager
			.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
		val navController = host.navController

		appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.main_dest,
				R.id.nodeList_dest,
				R.id.settings_dest,
				R.id.history_dest
			),//顶层导航设置
			mBinding.drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		mBinding.navView.setupWithNavController(navController)

		val headerView = mBinding.navView.getHeaderView(0)
		headerView.setOnClickListener {
			navController.navigate(R.id.login_dest)
			mBinding.drawerLayout.closeDrawers()
		}

		val mAvatar: ImageView = headerView.findViewById(R.id.avatar_img)
		val mUsername: TextView = headerView.findViewById(R.id.username_tv)
		val mAwardButton: TextView = headerView.findViewById(R.id.award)

		if (UserState.islogin) {
			mAwardButton.visible()
			mUsername.text = UserState.username
		}else{
			mAwardButton.gone()
			mUsername.text = resources.getString(R.string.action_sign_in)
		}


		Router.init(navController)
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

}
