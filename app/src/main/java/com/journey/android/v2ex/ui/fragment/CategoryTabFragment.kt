package com.journey.android.v2ex.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.common.base.Preconditions
import com.journey.android.v2ex.R
import com.journey.android.v2ex.model.Tab
import com.journey.android.v2ex.ui.MainActivity
import com.journey.android.v2ex.ui.MainFragmentDirections
import com.journey.android.v2ex.ui.TopicListFragment
import com.journey.android.v2ex.utils.PrefStore

class CategoryTabFragment : BaseTabFragment(),
    SharedPreferences.OnSharedPreferenceChangeListener,
    TopicListFragment.NavInterface {
  override fun navigate(id: Int) {
    val action = MainFragmentDirections.nextAction(id)
    findNavController().navigate(action)
  }

  private var isTabsChanged: Boolean = false

  override fun getAdapter(manager: FragmentManager): FragmentPagerAdapter {
    return CategoryFragmentAdapter(manager)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = super.onCreateView(inflater, container, savedInstanceState)

    val tabLayout = view!!.findViewById<View>(R.id.tab_layout) as TabLayout
    tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

    // XXX: TabLayout support tabContentStart, but no tabContentEnd, so set the padding manually
    val tabStrip = mTabLayout.getChildAt(0)
    Preconditions.checkNotNull(tabStrip, "tabStrip shouldn't be null")
    val padding = resources.getDimensionPixelSize(R.dimen.tab_layout_padding)
    ViewCompat.setPaddingRelative(tabStrip, padding, 0, padding, 0)

    return view
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    PrefStore.instance.registerPreferenceChangeListener(this)
  }

  override fun onStart() {
    super.onStart()

    if (isTabsChanged) {
      isTabsChanged = false
      activity!!.recreate()
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    PrefStore.instance.unregisterPreferenceChangeListener(this)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    //final MainActivity activity = (MainActivity) getActivity();
    //activity.setTitle(R.string.drawer_explore);
    //activity.setNavSelected(R.id.drawer_explore);
  }

  override fun onSharedPreferenceChanged(
    sharedPreferences: SharedPreferences,
    key: String
  ) {
    if (key == PrefStore.PREF_TABS_TO_SHOW) {
      isTabsChanged = true
    }
  }

  private inner class CategoryFragmentAdapter(manager: FragmentManager) : FragmentPagerAdapter(
      manager
  ) {
    private val mTabs: List<Tab> = PrefStore.instance.tabsToShow

    override fun getItem(position: Int): Fragment {
      return TopicListFragment.newInstance(mTabs[position].key, this@CategoryTabFragment)
    }

    override fun getCount(): Int {
      return mTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return mTabs[position].title
    }
  }

  companion object {
    fun newInstance(): CategoryTabFragment {
      return CategoryTabFragment()
    }
  }
}
