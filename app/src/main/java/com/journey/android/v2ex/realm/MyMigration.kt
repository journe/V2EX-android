package com.journey.android.v2ex.realm

import io.realm.DynamicRealm
import io.realm.RealmMigration

class MyMigration : RealmMigration {
  override fun migrate(
    realm: DynamicRealm,
    oldVersion: Long,
    newVersion: Long
  ) {

  }

}
