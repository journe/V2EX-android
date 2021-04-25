package com.journey.android.v2ex.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment : Fragment() {
  var _binding: ViewBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  open val binding get() = _binding!!

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

}

