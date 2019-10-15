package com.journey.android.v2ex.utils

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import com.journey.android.v2ex.model.Tab

class TabListPreference(
  context: Context,
  attrs: AttributeSet
) : ListPreference(context, attrs) {
  var mTabsToShow: List<Tab>? = null

//  override fun onSetInitialValue(
//    restorePersistedValue: Boolean,
//    defaultValue: Any?
//  ) {
//    if (restorePersistedValue) {
//      mTabsToShow = PrefStore.instance.tabsToShow
//    } else {
//      val string = defaultValue as String?
//      mTabsToShow = Tab.getTabsToShow(string)
//    }
//  }
//
//  override fun onCreateDialogView(): View {
//    return DragSortListView(context)
//  }
//
//  override fun onBindDialogView(view: View) {
//    super.onBindDialogView(view)
//
//  }
//
//  override fun onDialogClosed(positiveResult: Boolean) {
//    if (positiveResult) {
//      persistString(Tab.getStringToSave(mTabsToShow))
//    }
//    mTabsToShow = null
//  }
}
