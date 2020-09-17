package com.journey.android.v2ex.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.TopicsShowBean

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface TopicDetailDao : BaseDao<TopicsShowBean> {
  @Query("SELECT * FROM TopicsShowBean WHERE id = :topicId")
  fun getTopicById(topicId: Int): TopicsShowBean?

  @Query("DELETE FROM TopicsShowBean WHERE id = :topicId")
  suspend fun deleteTopic(topicId: Int)

}