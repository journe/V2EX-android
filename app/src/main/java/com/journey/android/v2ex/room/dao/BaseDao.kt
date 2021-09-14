package com.journey.android.v2ex.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertListSuspend(list: List<T>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(vararg obj: T)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(list: List<T>)

  @Update
  fun update(vararg obj: T)

  @Delete
  fun delete(vararg obj: T)

  @Delete
  suspend fun deleteSuspend(vararg obj: T)

}