package com.journey.android.v2ex

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.journey.android.v2ex.base.BaseActivity
import com.journey.android.v2ex.libs.transition.EdgeToEdge
import com.journey.android.v2ex.router.Router
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(main_toolbar)

    EdgeToEdge.setUpRoot(findViewById(R.id.drawer_layout))
    EdgeToEdge.setUpAppBar(app_bar,main_toolbar)
    EdgeToEdge.setUpScrollingContent(findViewById(R.id.main_constraint))

    val host: NavHostFragment = supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
    val navController = host.navController

    appBarConfiguration = AppBarConfiguration(
        setOf(R.id.main_dest, R.id.nodeList_dest, R.id.settings_dest),//顶层导航设置
        drawer_layout
    )
    setupActionBarWithNavController(navController, appBarConfiguration)
    nav_view?.setupWithNavController(navController)

    nav_view.getHeaderView(0)
        .setOnClickListener {
          navController.navigate(R.id.login_dest)
          drawer_layout.closeDrawers()
        }

    Router.init(this,navController)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    when (item.itemId) {
      R.id.action_settings -> return true
      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
  }

  override fun onDestroy() {
    super.onDestroy()
    RichText.recycle()
  }

}
