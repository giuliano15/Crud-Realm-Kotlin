package com.example.crudrealmteste

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            //.schemaVersion(1)
            //.migration(MyRealmMigration())
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(realmConfig)

    }
}

