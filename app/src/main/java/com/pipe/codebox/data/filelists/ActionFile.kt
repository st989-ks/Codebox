package com.pipe.codebox.data.filelists

import android.app.Application
import android.content.Context
import android.util.Log
import com.pipe.codebox.domain.entity.Locations
import com.yandex.mapkit.geometry.Point
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class ActionFile @Inject constructor(private val application: Application) {

    companion object {
        private const val CASH_FILE_NAME = "cash_file"
    }

    fun readContactsFiles(): MutableList<Locations> {
        createFirstFileIfNotExist()
        val inputText = StringBuffer()
        application.openFileInput(CASH_FILE_NAME).use { fileInputStream ->
            val dfd = fileInputStream.bufferedReader().readText()
            inputText.append(dfd)
        }
        Log.i(CASH_FILE_NAME, "readFiles $inputText")
        return if (inputText.length > 1) {
            jsonGet(inputText.toString())
        } else {
            mutableListOf(nullList)
        }
    }

    fun updateFile(list: MutableList<Locations>) {
        deleteFileIfNotExist()
        saveInFiles(list)
    }

    private fun saveInFiles(list: MutableList<Locations>) {
        val jsonText = jsonInFile(list)
        application.openFileOutput(CASH_FILE_NAME, Context.MODE_PRIVATE)
            .use { fileOutputStream ->
                fileOutputStream.write(jsonText.toByteArray())
            }
        Log.i(CASH_FILE_NAME, "saveAllInFiles $jsonText")
    }

    private fun createFirstFileIfNotExist() {
        val folder = File("${application.filesDir}")
        val file = File(folder.absolutePath + "/$CASH_FILE_NAME")
        if (!folder.exists()) {
            folder.mkdir()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        Log.i(CASH_FILE_NAME, "createFirstFileIfNotExist $file")
    }

    private fun deleteFileIfNotExist() {
        val folder = File("${application.filesDir}")
        val file = File(folder.absolutePath + "/$CASH_FILE_NAME")
        if (folder.exists()) {
            if (file.exists()) {
                file.delete()
            }
        }
        Log.i(CASH_FILE_NAME, "deleteFileIfNotExist")
        createFirstFileIfNotExist()
    }

    private fun jsonInFile(objects: MutableList<Locations>) =
        Json.encodeToString<MutableList<Locations>>(objects)

    private fun jsonGet(objects: String): MutableList<Locations> =
        Json.decodeFromString<MutableList<Locations>>(objects)

    private val nullList = Locations("Moscow", Point(55.751574, 37.573856))


}