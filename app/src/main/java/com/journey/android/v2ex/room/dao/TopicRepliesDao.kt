package com.journey.android.v2ex.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.model.api.TopicsShowBean

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface TopicRepliesDao : BaseDao<RepliesShowBean> {
  @Query("SELECT * FROM RepliesShowBean")
  fun getAll(): List<RepliesShowBean>

  @Query("SELECT * FROM RepliesShowBean WHERE topic_id = :topicId ORDER BY floor ASC")
  fun getTopicReplies(topicId: Int): DataSource.Factory<Int, RepliesShowBean>

  @Query("DELETE FROM RepliesShowBean")
  suspend fun deleteAll()
}