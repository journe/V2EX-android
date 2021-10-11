package com.journey.android.v2ex.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.TopicsListItemBean

/**
 * Created by journey on 2020/5/20.
 */
@Dao
interface TopicListDao : BaseDao<TopicsListItemBean> {
    @Query("SELECT * FROM TopicsListItemBean ORDER BY indexInResponse ASC")
    fun pagingSource(): PagingSource<Int, TopicsListItemBean>

    @Query("SELECT * FROM TopicsListItemBean WHERE tab=:tab ORDER BY indexInResponse ASC")
    fun pagingSource(tab: String): PagingSource<Int, TopicsListItemBean>

    @Query("SELECT COUNT(id) FROM TopicsListItemBean WHERE tab=:tab ")
    fun topicCount(tab: String): Int

    @Query("SELECT * FROM TopicsListItemBean WHERE id=:id ")
    fun getTopicById(id: Int): TopicsListItemBean

    @Query("SELECT MAX(indexInResponse) + 1 FROM TopicsListItemBean")
    fun getNextIndex(): Int

    @Query("DELETE FROM TopicsListItemBean")
    fun deleteAll()
}