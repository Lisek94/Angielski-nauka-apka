package com.example.angielskinauka

import android.annotation.SuppressLint
import android.widget.ProgressBar
import android.widget.TextView

class ProgressBarManager {
    private val listLearnedSize = ChapterManager.arrayLearnedList.size.toDouble()
    private val listNotLearnedSize = ChapterManager.arrayNotLearnedList.size.toDouble()

    private fun checkProgress(): Int{
        val progressPercent = (listLearnedSize/(listNotLearnedSize + listLearnedSize))*100
        return progressPercent.toInt()
    }

    @SuppressLint("SetTextI18n")
    fun setProgressBar(progressBar:ProgressBar,percentTextView:TextView) {
        val progressPercent = checkProgress()
        progressBar.progress = progressPercent
        percentTextView.text = "$progressPercent %"
    }

}