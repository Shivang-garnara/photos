package com.qtonz.photos.gallery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentlyOpenedImage::class], version = 1)
abstract class RecentlyOpenedImageDatabase : RoomDatabase() {
    abstract fun recentlyOpenedImageDao(): RecentlyOpenedImageDao

    companion object {
        @Volatile
        private var INSTANCE: RecentlyOpenedImageDatabase? = null

        fun getDatabase(context: Context): RecentlyOpenedImageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecentlyOpenedImageDatabase::class.java,
                    "recently_opened_images_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
