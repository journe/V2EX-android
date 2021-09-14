package com.journey.android.v2ex.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.RepliesShowBean

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface TopicRepliesDao : BaseDao<RepliesShowBean> {
  @Query("SELECT * FROM RepliesShowBean")
  fun getAll(): List<RepliesShowBean>

  @Query("SELECT * FROM RepliesShowBean WHERE topic_id = :topicId ORDER BY floor ASC")
  fun pagingSource(topicId: Int): PagingSource<Int, RepliesShowBean>

  @Query("DELETE FROM RepliesShowBean")
  suspend fun deleteAll()
}