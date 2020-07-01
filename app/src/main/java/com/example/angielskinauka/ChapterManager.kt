package com.example.angielskinauka

import android.content.Context
import kotlin.random.Random

class ChapterManager(context: Context) {
    private var dataBaseManager = DataBaseManager(context)
    private var arrayChapterList = ArrayList<String>()
    private var arrayLearnedList = ArrayList<String>()
    private var chapter = Chapter()

    fun createArrayChapters(): ArrayList<String>{
        val dataSize = dataBaseManager.getDataSize()
        for(i in 1..dataSize){
            dataBaseManager.getChapter(i)
            arrayChapterList.add(chapter.chapterName)
        }
        return arrayChapterList
    }

    fun createDefaultChapters(chapterCount: Int):ChapterManagerStatus{
        var status = ChapterManagerStatus.StatusAllSaveComplete
        if(chapterCount > 0 ){
            for (i in 1..chapterCount){
                chapter.chapterName = i.toString()
                if (!dataBaseManager.saveChapter(chapter)) {
                    status = ChapterManagerStatus.StatusDataException
                }
            }
        } else {
            status = ChapterManagerStatus.StatusNotDataToSave
        }
        return status
    }
    fun isAnyChaptersExists():Boolean{
        return dataBaseManager.isDataEmpty()
    }

    fun addOneChapter(chapterNumber: String): ChapterManagerStatus {
        return if (chapterNumber.isNotEmpty() ) {
            chapter.chapterName = chapterNumber
                if (dataBaseManager.saveChapter(chapter)){
                    ChapterManagerStatus.StatusSingleSaveComplete
                } else {
                    ChapterManagerStatus.StatusDataException
                }
            } else {
                ChapterManagerStatus.StatusNotDataToSave
            }
    }

    fun randomChapterNumber():String {
        val dataSize = arrayLearnedList.size
        val randomInt = Random.nextInt(0,dataSize)
        return arrayLearnedList.get(randomInt).toString()
    }

}
enum class ChapterManagerStatus{
    StatusSingleSaveComplete,
    StatusAllSaveComplete,
    StatusNotDataToSave,
    StatusDataException,
}