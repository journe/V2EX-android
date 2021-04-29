package com.journey.android.v2ex.module.topic.detail

import android.R.transition
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentTopicDetailBinding
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.module.topic.detail.adapter.TopicCommentAdapter
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderAdapter
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderSubtleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_topic_detail.coordinatorLayout
import kotlinx.android.synthetic.main.fragment_topic_detail.topic_detail_comments_list
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicDetailFragment : BaseFragment() {

  companion object {
    const val TRANSITION_HEADER_AVATAR = "header_avatar"
    const val TRANSITION_HEADER_TITLE = "header_title"
    const val TRANSITION_TOOLBAR = "toolbar"
    const val TRANSITION_BACKGROUND = "background"
    const val TRANSITION_FAVORITE = "favorite"
    const val TRANSITION_BOOKMARK = "bookmark"
    const val TRANSITION_SHARE = "share"
    const val TRANSITION_BODY = "body"
  }

  private val safeArgs: TopicDetailFragmentArgs by navArgs()
  private val viewModel: TopicDetailViewModel by viewModels()
  override val binding get() = _binding!! as FragmentTopicDetailBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    postponeEnterTransition()
    sharedElementEnterTransition = TransitionInflater.from(context)
      .inflateTransition(transition.move)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentTopicDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    ViewCompat.setTransitionName(coordinatorLayout, TRANSITION_BACKGROUND)

    viewModel.initTopicDetail(safeArgs.topicId)

    binding.topicDetailCommentsList.addItemDecoration(
      DividerItemDecoration(
        context,
        DividerItemDecoration.VERTICAL
      )
    )
    binding.topicDetailTitleTv.text = safeArgs.topicTitle
//    startPostponedEnterTransition()
//    topic_detail_title_tv.transitionName = "header_title"
//    topic_detail_avatar.transitionName = "header_avatar"

    val request = ImageRequest.Builder(requireContext())
      .data(safeArgs.avatar)
      .target(
        onStart = { placeholder ->
          // Handle the placeholder drawable.
        },
        onSuccess = { result ->
          // Handle the successful result.
          startPostponedEnterTransition()
          binding.topicDetailAvatar.load(result)
        },
        onError = { error ->
          // Handle the error drawable.
          startPostponedEnterTransition()
        }
      )
      .build()
    requireContext().imageLoader.enqueue(request)

    getData()
  }

  private fun getData() {
//    viewModel.initTopicDetail()
    val topicHeaderAdapter = TopicHeaderAdapter(TopicsShowBean())
    val topicHeaderSubtleAdapter = TopicHeaderSubtleAdapter(TopicsShowBean())
    val topicCommentAdapter = TopicCommentAdapter()

    val concatAdapter = ConcatAdapter().apply {
      addAdapter(topicHeaderAdapter)
      addAdapter(topicHeaderSubtleAdapter)
      addAdapter(topicCommentAdapter)
    }

    topic_detail_comments_list.adapter = concatAdapter

    viewModel.getTopicsShowBean(safeArgs.topicId).observe(viewLifecycleOwner, {
      if (it != null) {
        binding.topicDetailNodeTv.text = it.node.name
        binding.topicDetailCreateTimeTv.text = it.created_str

        topicHeaderAdapter.topicDetailBean = it
        topicHeaderAdapter.notifyDataSetChanged()
        topicHeaderSubtleAdapter.topicDetailBean = it
        topicHeaderSubtleAdapter.notifyDataSetChanged()
      }
    })

//    lifecycleScope.launch {
//      viewModel.getTopicReplyBean(safeArgs.topicId)
//        .collectLatest {
//          topicCommentAdapter.submitData(it)
//        }
//
//    }

  }

}
