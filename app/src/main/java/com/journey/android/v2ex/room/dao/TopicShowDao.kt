package com.journey.android.v2ex.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.TopicsShowBean

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface TopicShowDao : BaseDao<TopicsShowBean> {
  @Query("SELECT * FROM TopicsShowBean WHERE id = :topicId")
  fun getTopicById(topicId: Int): LiveData<TopicsShowBean?>

}