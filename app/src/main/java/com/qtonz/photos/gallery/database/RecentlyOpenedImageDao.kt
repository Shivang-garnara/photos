package com.qtonz.photos.gallery.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//@Dao
//interface RecentlyOpenedImageDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertImage(image: RecentlyOpenedImage)
//
//    @Query("SELECT * FROM recently_opened_images ORDER BY id DESC")
//    suspend fun getAllRecentlyOpenedImages(): List<RecentlyOpenedImage>
//}

//@Dao
//interface RecentlyOpenedImageDao {
//    @Query("SELECT * FROM recently_opened_images")
//    fun getAllRecentlyOpenedImages(): List<RecentlyOpenedImage>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertImage(recentlyOpenedImage: RecentlyOpenedImage)
//
//    @Query("SELECT * FROM recently_opened_images WHERE imagePath = :imagePath LIMIT 1")
//    fun getImageByPath(imagePath: String): RecentlyOpenedImage?
//}


@Dao
interface RecentlyOpenedImageDao {
    @Query("SELECT * FROM recently_opened_images")
    fun getAllRecentlyOpenedImages(): LiveData<List<RecentlyOpenedImage>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(recentlyOpenedImage: RecentlyOpenedImage)

    @Query("SELECT * FROM recently_opened_images WHERE imagePath = :imagePath LIMIT 1")
    fun getImageByPath(imagePath: String): RecentlyOpenedImage?
}

