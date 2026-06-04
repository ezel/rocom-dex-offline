package org.martin.rocomdex

import android.app.Application
import android.util.Log
import androidx.room.Room
import org.martin.rocomdex.data.DexDatabase

class RDApplication: Application() {
    lateinit var database: DexDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            DexDatabase::class.java, "data.db"
        ).createFromAsset("database/rocom.db").fallbackToDestructiveMigration(false).build()
        Log.d("RDApplication","Created database from asset")
    }
}