package com.journey.android.v2ex.module.topic

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import com.google.android.material.appbar.AppBarLayout
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.libs.transition.FAST_OUT_LINEAR_IN
import com.journey.android.v2ex.libs.transition.LARGE_COLLAPSE_DURATION
import com.journey.android.v2ex.libs.transition.LARGE_EXPAND_DURATION
import com.journey.android.v2ex.libs.transition.LINEAR_OUT_SLOW_IN
import com.journey.android.v2ex.libs.transition.plusAssign
import com.journey.android.v2ex.libs.transition.transitionTogether
import com.journey.android.v2ex.model.Tab
import com.journey.android.v2ex.module.topic.list.TopicListFragment
import com.journey.android.v2ex.utils.PrefStore
import kotlinx.android.synthetic.main.fragment_main.main_tab
import kotlinx.android.synthetic.main.fragment_main.main_viewpager

class MainFragment : BaseFragment() {

  val fragments =
    PrefStore.instance.tabsToShow.map {
      TopicListFragment.newInstance(it.key)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_main, container, false)

  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    val myPagerAdapter = MainPagerAdapter(childFragmentManager)
    main_viewpager.adapter = myPagerAdapter
    main_tab.setupWithViewPager(main_viewpager)

  }

  inner class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mTabs: List<Tab> = PrefStore.instance
        .tabsToShow

    override fun getItem(position: Int): Fragment {
      return fragments[position]
    }

    override fun getCount(): Int {
      return mTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return mTabs[position].title
    }
  }
}
