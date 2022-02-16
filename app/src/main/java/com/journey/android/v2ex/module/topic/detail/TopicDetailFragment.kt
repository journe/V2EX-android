package com.journey.android.v2ex.module.topic.detail

import android.R.transition
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentTopicDetailBinding
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.model.api.TopicShowSubtle
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.module.topic.detail.adapter.TopicCommentAdapter
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderAdapter
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderSubtleAdapter
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderTagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TopicDetailFragment : BaseFragment<FragmentTopicDetailBinding, TopicDetailViewModel>() {

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
  override val mViewModel: TopicDetailViewModel by viewModels()

  private val topicHeaderAdapter = TopicHeaderAdapter(TopicsShowBean())
  private val topicHeaderSubtleAdapter = TopicHeaderSubtleAdapter(emptyList())
  private val topicHeaderTagAdapter = TopicHeaderTagAdapter(emptyList())
  private val topicCommentAdapter = TopicCommentAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    postponeEnterTransition()
    sharedElementEnterTransition = TransitionInflater.from(context)
      .inflateTransition(transition.move)
  }

  override fun FragmentTopicDetailBinding.initView() {
    mBinding.topicDetailTitleTv.text = safeArgs.topicTitle

    ViewCompat.setTransitionName(coordinatorLayout, TRANSITION_BACKGROUND)

    mBinding.topicDetailCommentsList.adapter = ConcatAdapter().apply {
      addAdapter(topicHeaderAdapter)
      addAdapter(topicHeaderSubtleAdapter)
      addAdapter(topicHeaderTagAdapter)
      addAdapter(topicCommentAdapter)
    }

    mBinding.topicDetailCommentsList.addItemDecoration(
      DividerItemDecoration(
        context,
        DividerItemDecoration.VERTICAL
      )
    )

    val request = ImageRequest.Builder(requireContext())
      .data(safeArgs.avatar)
      .target(
        onStart = { placeholder ->
          // Handle the placeholder drawable.
        },
        onSuccess = { result ->
          // Handle the successful result.
          startPostponedEnterTransition()
          mBinding.topicDetailAvatar.load(result)
        },
        onError = { error ->
          // Handle the error drawable.
          startPostponedEnterTransition()
        }
      )
      .build()
    requireContext().imageLoader.enqueue(request)
//    startPostponedEnterTransition()
//    topic_detail_title_tv.transitionName = "header_title"
//    topic_detail_avatar.transitionName = "header_avatar"

  }

  override fun initObserve() {

    mViewModel.getTopicsShowBean(safeArgs.topicId).observe(viewLifecycleOwner) {
      if (it != null) {
        mBinding.topicDetailNodeTv.text = it.node.name
        mBinding.topicDetailCreateTimeTv.text = it.created_str

        topicHeaderAdapter.topicDetailBean = it
        topicHeaderAdapter.notifyDataSetChanged()
        topicHeaderSubtleAdapter.list = it.subtles
        topicHeaderSubtleAdapter.notifyDataSetChanged()
        topicHeaderTagAdapter.list = it.topic_tags
        topicHeaderTagAdapter.notifyDataSetChanged()
      }
    }

    launch({
      mViewModel.getTopicReplyPager(safeArgs.topicId)
        .collectLatest {
          topicCommentAdapter.submitData(it)
        }
    })
  }

  override fun initRequestData() {
    mViewModel.initTopicDetail(safeArgs.topicId)
  }

}
