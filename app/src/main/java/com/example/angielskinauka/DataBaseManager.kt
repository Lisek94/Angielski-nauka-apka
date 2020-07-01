package com.example.angielskinauka

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

class DataBaseManager(context: Context) {
    private var dBHelper:DataBaseHelper = DataBaseHelper(context)
    private var db:SQLiteDatabase = dBHelper.writableDatabase

    fun isDataEmpty():Boolean{
        val cursor = db.query(TableInfo.TABLE_NAME,null,null,null,null,null,null)
        val result = cursor.count == 0
        cursor.close()
        return result
    }

    fun getDataSize(): Int{
        val cursor = db.query(TableInfo.TABLE_NAME,null,null,null,null,null,null)
        val result = cursor.count
        cursor.close()
        return result
    }

    fun getChapter(cursorPosition:Int):Chapter{
        val chapter = Chapter()
        val cursor = db.query(TableInfo.TABLE_NAME,null,null,null,null,null,null)
        cursor.moveToPosition(cursorPosition+1)
        chapter.chapterName = cursor.getString(1)
        chapter.isLearned = cursor.getString(2)
        return chapter
    }

    fun saveChapter(chapter:Chapter):Boolean{
        val value = ContentValues()
        return try {
            value.put("title", chapter.chapterName )
            value.put("isLearned", chapter.isLearned)
            db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
            true
        } catch (e:SQLiteException){
            false
        }
    }
}