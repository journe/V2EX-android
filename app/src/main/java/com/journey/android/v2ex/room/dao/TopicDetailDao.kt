package com.journey.android.v2ex.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.model.jsoup.TopicDetailBean

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface TopicDetailDao : BaseDao<TopicDetailBean> {
  @Query("SELECT * FROM TopicDetailBean WHERE id = :topicId")
  fun getTopicById(topicId: Int): TopicDetailBean?
}