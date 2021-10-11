package com.journey.android.v2ex.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.model.api.TopicsShowBean
import kotlinx.coroutines.flow.Flow

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface TopicShowDao : BaseDao<TopicsShowBean> {
  @Query("SELECT * FROM TopicsShowBean WHERE id = :topicId")
  fun getTopicById(topicId: Int): Flow<TopicsShowBean>


  @Query("SELECT * FROM TopicsShowBean ORDER BY local_touched DESC")
  fun historyPagingSource(): PagingSource<Int, TopicsShowBean>

}