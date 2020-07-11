package com.example.angielskinauka

import android.content.Context
import com.example.angielskinauka.data.DataBaseManager
import kotlin.random.Random

class ChapterManager(context: Context) {
    companion object{
        var arrayNotLearnedList = ArrayList<String>()
        var arrayLearnedList = ArrayList<String>()
    }

    private var dataBaseManager = DataBaseManager(context)

    fun createArrays(){
        var chapter: Chapter
        val dataSize = dataBaseManager.getDataSize()
        for(i in 0 until dataSize){
            chapter = dataBaseManager.getChapter(i)
            if(checkStatusChapters(chapter)){
                arrayLearnedList.add(chapter.chapterName)
            } else {
                arrayNotLearnedList.add(chapter.chapterName)
            }
        }
    }

    private fun checkStatusChapters(chapter: Chapter):Boolean{
        return chapter.isLearned == "true"
    }

    fun createDefaultChapters(chapterCount: Int):ChapterManagerStatus{
        val chapter = Chapter()
        var status = ChapterManagerStatus.StatusAllSaveComplete
        if(chapterCount > 0 ){
            for (i in 1..chapterCount){
                chapter.chapterName = i.toString()
                if (!dataBaseManager.saveChapter(chapter)) {
                    status = ChapterManagerStatus.StatusDataException
                }
            }
            createArrays()
        } else {
            status = ChapterManagerStatus.StatusNotDataToSave
        }
        return status
    }
    fun isAnyChaptersExists():Boolean{
        return dataBaseManager.isDataEmpty()
    }

    fun addOneChapter(chapterInput: String): ChapterManagerStatus {
        val chapter = Chapter()
        chapter.chapterName = chapterInput
        return if (dataBaseManager.saveChapter(chapter)){
            createArrays()
                    ChapterManagerStatus.StatusSingleSaveComplete
                } else {
                    ChapterManagerStatus.StatusDataException
                }
    }

    fun randomChapterNumber():String {
        val dataSize = arrayNotLearnedList.size
        val randomInt = Random.nextInt(0,dataSize)
        updateChapterStatus(randomInt)
        return arrayNotLearnedList[randomInt]
    }

    private fun updateChapterStatus(numberChapter: Int){
        val chapter = Chapter()
        chapter.chapterName = numberChapter.toString()
        chapter.isLearned = "true"
        dataBaseManager.editChapter(chapter)
    }
}

enum class ChapterManagerStatus {
    StatusSingleSaveComplete,
    StatusAllSaveComplete,
    StatusNotDataToSave,
    StatusDataException,
}