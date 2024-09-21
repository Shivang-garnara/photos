package com.qtonz.photos.gallery.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_opened_images")
data class RecentlyOpenedImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imagePath: String
)
