package com.journey.android.v2ex.utils

import android.content.Context
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.View
import android.widget.ArrayAdapter
import com.journey.android.v2ex.model.Tab
import com.journey.android.v2ex.view.DragSortListView

class TabListPreference(
  context: Context,
  attrs: AttributeSet
) : DialogPreference(context, attrs) {
  var mTabsToShow: List<Tab>? = null

  override fun onSetInitialValue(
    restorePersistedValue: Boolean,
    defaultValue: Any?
  ) {
    if (restorePersistedValue) {
      mTabsToShow = PrefStore.instance.tabsToShow
    } else {
      val string = defaultValue as String?
      mTabsToShow = Tab.getTabsToShow(string)
    }
  }

  override fun onCreateDialogView(): View {
    return DragSortListView(context)
  }

  override fun onBindDialogView(view: View) {
    super.onBindDialogView(view)
    if (mTabsToShow == null) {
      onSetInitialValue(true, null)
    }

    val adapter = StableArrayAdapter(
        context,
        android.R.layout.simple_list_item_1, mTabsToShow
    )
    val listView = view as DragSortListView
    listView.setDataList(mTabsToShow)
    listView.adapter = adapter
  }

  override fun onDialogClosed(positiveResult: Boolean) {
    if (positiveResult) {
      persistString(Tab.getStringToSave(mTabsToShow))
    }
    mTabsToShow = null
  }
}
