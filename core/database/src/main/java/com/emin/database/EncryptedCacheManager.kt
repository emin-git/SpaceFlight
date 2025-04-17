package com.emin.database


import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import com.emin.model.news.Result

class EncryptedCacheManager(private val context: Context) {

    private val fileName = "news_cache.json"
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    fun save(newsList: List<Result>) {
        try {
            val file = File(context.filesDir, fileName)

            if (file.exists()) {
                file.delete()
            }

            val encryptedFile = EncryptedFile.Builder(
                file,
                context,
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            val gson = Gson()
            val json = gson.toJson(newsList)

            encryptedFile.openFileOutput().use { outputStream ->
                outputStream.write(json.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun read(): List<Result>? {
        try {
            val file = File(context.filesDir, fileName)

            if (!file.exists()) {
                return null
            }

            val encryptedFile = EncryptedFile.Builder(
                file,
                context,
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            val json = encryptedFile.openFileInput().bufferedReader().use { it.readText() }

            val gson = Gson()
            val type = object : TypeToken<List<Result>>() {}.type
            return gson.fromJson(json, type)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}