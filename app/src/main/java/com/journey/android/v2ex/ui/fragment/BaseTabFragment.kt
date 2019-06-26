package com.journey.android.v2ex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.journey.android.v2ex.R

abstract class BaseTabFragment : Fragment() {
  protected lateinit var mTabLayout: TabLayout

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_cate_tab_layout, container, false)
    val viewPager = view.findViewById<View>(R.id.view_pager) as ViewPager
    val adapter = getAdapter(childFragmentManager)
    viewPager.adapter = adapter
    viewPager.pageMargin = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)

    mTabLayout = view.findViewById<View>(R.id.tab_layout) as TabLayout
    ViewCompat.setElevation(mTabLayout, resources.getDimension(R.dimen.appbar_elevation))
    mTabLayout.setupWithViewPager(viewPager)

    return view
  }

  protected abstract fun getAdapter(manager: FragmentManager): FragmentPagerAdapter
}// Required empty public constructor
