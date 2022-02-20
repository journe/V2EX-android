package com.journey.android.v2ex.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.NodeBean

/**
 * Created by journey on 2020/5/20.
 */
@Dao
interface NodeListDao : BaseDao<NodeBean> {
	@Query("SELECT * FROM NodeBean")
	fun pagingSource(): PagingSource<Int, NodeBean>

	@Query("SELECT COUNT(id) FROM NodeBean")
	fun count(): Int

	@Query("DELETE FROM NodeBean")
	fun deleteAll()
}