package com.journey.android.v2ex.module.topic

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.base.EmptyViewModel
import com.journey.android.v2ex.databinding.FragmentMainBinding
import com.journey.android.v2ex.model.Tab
import com.journey.android.v2ex.module.topic.list.TopicListFragment
import com.journey.android.v2ex.utils.PrefStore
import kotlinx.android.synthetic.main.fragment_main.main_viewpager

class MainFragment : BaseFragment<FragmentMainBinding, EmptyViewModel>() {

  override val mViewModel: EmptyViewModel by viewModels()
  val fragments =
    PrefStore.instance.tabsToShow.map {
      TopicListFragment.newInstance(it.key)
    }

  inner class MainPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mTabs: List<Tab> = PrefStore.instance.tabsToShow

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

  override fun FragmentMainBinding.initView() {
    val myPagerAdapter = MainPagerAdapter(childFragmentManager)
    mBinding.mainViewpager.adapter = myPagerAdapter
    mBinding.mainTab.setupWithViewPager(main_viewpager)
  }

  override fun initObserve() {
  }

  override fun initRequestData() {
  }
}
