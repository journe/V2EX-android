package com.journey.android.v2ex.module.topic.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.journey.android.v2ex.base.BaseRepository
import com.journey.android.v2ex.room.dao.TopicListDao
import com.journey.android.v2ex.room.dao.TopicShowDao
import javax.inject.Inject

class TopicHistoryRepository @Inject constructor(private val db: TopicShowDao) : BaseRepository() {

    fun getList() = Pager(PagingConfig(pageSize = 20)) { db.historyPagingSource() }.flow

}