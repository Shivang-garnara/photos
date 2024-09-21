package com.qtonz.photos.galleryjson.utils

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object RecentImagesJsonHelper{

    private const val FILE_NAME = "recent_images.json"

    fun saveRecentImage(context: Context, imagePath: String) {
        val file = File(context.filesDir, FILE_NAME)
        val jsonArray: JSONArray = if (file.exists()) {
            JSONArray(file.readText())
        } else {
            JSONArray()
        }

        val jsonObject = JSONObject().apply {

            put("path", imagePath)
            put("timestamp", System.currentTimeMillis())
        }

        jsonArray.put(jsonObject)
        file.writeText(jsonArray.toString())
    }

    fun getRecentImages(context: Context): List<String> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return emptyList()

        val jsonArray = JSONArray(file.readText())
        val imagePaths = mutableListOf<String>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            imagePaths.add(jsonObject.getString("path"))
        }

        return imagePaths
    }
}