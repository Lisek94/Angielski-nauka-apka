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

    private fun checkStatusChapters(chapter: Chapter):Boolean{
        return chapter.isLearned == "true"
    }

    private fun clearArrays(){
        arrayNotLearnedList.clear()
        arrayLearnedList.clear()
    }

    fun createArrays(){
        var chapter: Chapter
        val dataSize = dataBaseManager.getDataSize()
        clearArrays()

        for(i in 0 until dataSize){
            chapter = dataBaseManager.getChapter(i)
            if(checkStatusChapters(chapter)){
                arrayLearnedList.add(chapter.chapterName)
            } else {
                arrayNotLearnedList.add(chapter.chapterName)
            }
        }
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
        } else {
            status = ChapterManagerStatus.StatusNotDataToSave
        }
        return status
    }

    fun isAnyChaptersExists():Boolean{
        return dataBaseManager.isDataEmpty()
    }

    private fun checkIsChapterIsRepeatForArrayNotLearned(chapterNumber:String):Boolean{
        var bool = true
        for(i in 0 until arrayNotLearnedList.size){
            if(arrayNotLearnedList[i] == chapterNumber) {
                bool = false
                break
            }
        }
        return bool
    }

    private fun checkIsChapterIsRepeatForArrayLearned(chapterNumber:String):Boolean{
        var bool = true
        for(i in 0 until arrayLearnedList.size){
            if(arrayLearnedList[i] == chapterNumber){
                bool = false
                break
            }
        }
        return bool
    }

    fun addOneChapter(chapterInput: String): ChapterManagerStatus {
        val chapter = Chapter()
        chapter.chapterName = chapterInput
        return if(checkIsChapterIsRepeatForArrayLearned(chapterInput)
            &&checkIsChapterIsRepeatForArrayNotLearned(chapterInput)) {
            if (dataBaseManager.saveChapter(chapter)){
                ChapterManagerStatus.StatusSingleSaveComplete
            } else {
                ChapterManagerStatus.StatusDataException
            }
        } else {
            ChapterManagerStatus.StatusChapterExists
        }
    }

    fun randomChapterNumber():String {
        val dataSize = arrayNotLearnedList.size
        val randomInt = Random.nextInt(0,dataSize)
        updateChapterStatus(arrayNotLearnedList[randomInt].toInt())
        return arrayNotLearnedList[randomInt]
    }

    fun checkListLearnedSize():Boolean{
        val dataSize = arrayNotLearnedList.size
        return dataSize > 0
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
    StatusChapterExists
}