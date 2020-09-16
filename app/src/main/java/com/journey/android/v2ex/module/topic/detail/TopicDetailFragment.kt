package com.journey.android.v2ex.module.topic.detail

import android.R.transition
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.router.V2EXRouter
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_topic_detail.topic_detail_comments_list
import kotlinx.android.synthetic.main.fragment_topic_detail.topic_detail_head_cl

class TopicDetailFragment : BaseFragment() {

  val safeArgs: TopicDetailFragmentArgs by navArgs()

  private val viewModel: TopicDetailViewModel by viewModels {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopicDetailViewModel(
            TopicDetailRepository(
                AppDatabase.getInstance(),
                safeArgs.topicId
            )
        ) as T
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    postponeEnterTransition()
    sharedElementEnterTransition = TransitionInflater.from(context)
        .inflateTransition(
            transition.move
        )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_topic_detail, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    topic_detail_comments_list.addItemDecoration(
        DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
    )
    getData()
  }

  private fun getData() {
    val adapter = TopicCommentAdapter()
    topic_detail_comments_list.adapter = adapter

    viewModel.topicDetailBean.observe(viewLifecycleOwner, {
      addHeaderView(it)
    })
    viewModel.repliesShowBean.observe(viewLifecycleOwner, {
//      topic_list_refreshview.isRefreshing = false
      adapter.submitList(it)
    })

  }

  private fun addHeaderView(topicDetailBean: TopicsShowBean) {
    //取得评论数据并添加head view
//    val headView = layoutInflater.inflate(
//        R.layout.fragment_topic_detail_head,
//        view as ViewGroup, false
//    )
    val headView = topic_detail_head_cl
    headView.findViewById<TextView>(R.id.topic_detail_title_tv)
        .text = topicDetailBean.title
    headView.findViewById<TextView>(R.id.topic_detail_node_tv)
        .text = topicDetailBean.node.name
    headView.findViewById<TextView>(R.id.topic_detail_create_time_tv)
        .text = topicDetailBean.created_str
    topicDetailBean.content?.let {
      RichText.fromHtml(it)
          .clickable(true)
          .imageClick { imageUrls, position ->
            showImage(imageUrls[position])
          }
          .into(headView.findViewById(R.id.topic_detail_content_tv))
    }
//    ImageLoader.loadImage(
//        headView.findViewById(R.id.topic_detail_avatar), topicDetailBean.member.avatar_large
//    )
    Glide.with(this)
        .load(topicDetailBean.member.avatar_large)
        .centerCrop()
        .dontAnimate()
        .listener(object : RequestListener<Drawable> {
          override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
          ): Boolean {
            startPostponedEnterTransition()
            return false
          }

          override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
          ): Boolean {
            startPostponedEnterTransition()
            return false
          }
        })
        .into(headView.findViewById(R.id.topic_detail_avatar))

    //附言
    val subtlesView = headView.findViewById<LinearLayout>(R.id.topic_subtles_ll)
    topicDetailBean.subtles?.forEach {
      val subtleItemView = layoutInflater.inflate(
          R.layout.fragment_topic_detail_subtle_item,
          view as ViewGroup, false
      )
      subtleItemView.findViewById<TextView>(R.id.topic_subtle_title_tv)
          .text = it.title
      RichText.fromHtml(it.content)
          .clickable(true)
          .imageClick { imageUrls, position ->
            showImage(imageUrls[position])
          }
          .into(subtleItemView.findViewById(R.id.topic_subtle_content_tv))
      subtlesView.addView(subtleItemView)
    }
  }

  private fun showImage(url: String?) {
    val action =
      TopicDetailFragmentDirections.topicDetailImage(url ?: "")
    V2EXRouter.navigate(action)
  }

}
