package com.buffalomilkpredictor.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buffalomilkpredictor.data.model.BuffaloAnalysisEntity

@Database(
    entities = [BuffaloAnalysisEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BuffaloDatabase : RoomDatabase() {
    abstract fun buffaloAnalysisDao(): BuffaloAnalysisDao

    companion object {
        @Volatile
        private var instance: BuffaloDatabase? = null

        fun getInstance(context: Context): BuffaloDatabase {
            return instance ?: synchronized(this) {
                instance ?: createDatabase(context).also { instance = it }
            }
        }

        private fun createDatabase(context: Context): BuffaloDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BuffaloDatabase::class.java,
                "buffalo_database"
            ).build()
        }
    }
}
