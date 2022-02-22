package com.journey.android.v2ex.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.journey.android.v2ex.model.api.MemberBean
import kotlinx.coroutines.flow.Flow

/**
 * Created by journey on 2020/5/19.
 */
@Dao
interface UserInfoDao : BaseDao<MemberBean> {
	@Query("SELECT * FROM MemberBean")
	fun getAll(): List<MemberBean>

	@Query("SELECT * FROM MemberBean WHERE id = :id LIMIT 1")
	suspend fun getUserById(id: String): MemberBean?

	@Query("SELECT * FROM MemberBean WHERE username = :name LIMIT 1")
	fun getUserByName(name: String): Flow<MemberBean?>

	@Query("SELECT * FROM MemberBean WHERE id = :id LIMIT 1")
	fun getUserByIdLive(id: String): LiveData<MemberBean>?
}