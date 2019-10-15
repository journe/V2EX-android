package com.journey.android.v2ex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import com.journey.android.v2ex.R
import com.journey.android.v2ex.model.Tab
import com.journey.android.v2ex.ui.fragment.TopicListFragment.NavInterface
import com.journey.android.v2ex.utils.PrefStore
import kotlinx.android.synthetic.main.fragment_main.main_tab
import kotlinx.android.synthetic.main.fragment_main.main_viewpager

class MainFragment : BaseFragment(),
    NavInterface {
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
    val myPagerAdapter = childFragmentManager?.let { MainPagerAdapter(it) }
    main_viewpager.adapter = myPagerAdapter
    main_tab.setupWithViewPager(main_viewpager)
  }

  override fun navigate(id: Int) {
    val action = MainFragmentDirections.nextAction(id)
    findNavController().navigate(action)
  }

  inner class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mTabs: List<Tab> = PrefStore.instance
        .tabsToShow

    override fun getItem(position: Int): Fragment {
      return TopicListFragment.newInstance(
          mTabs[position].key, this@MainFragment
      )
    }

    override fun getCount(): Int {
      return mTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return mTabs[position].title
    }
  }
}
