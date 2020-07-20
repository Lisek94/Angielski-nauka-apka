package com.example.angielskinauka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val inputManager = InputManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chapterManager = ChapterManager(this)
        firstStart(chapterManager)

        addChapterButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER

            builder.setView(editText)
            builder.setMessage("Wpisz numer rozdziału")
                .setPositiveButton("Zapisz") { _, _ ->
                    if (inputManager.checkEditTextForNull(editText)){
                        showToastForSaveChapter(chapterManager.addOneChapter(editText.text.toString()))
                    } else {
                        showToastForSaveChapter(ChapterManagerStatus.StatusNotDataToSave)
                    }
                    chapterManager.createArrays()
                }
                .setNegativeButton("Anuluj") {dialog,_ ->
                    dialog.cancel()
                }
            builder.show()
        }

        randomChapterButton.setOnClickListener{
            showToastForRandomChapter(chapterManager.randomChapterNumber())
            chapterManager.createArrays()
        }

        showNotCompleteChapterButton.setOnClickListener {
            val intent = Intent(this,ArrayChapterActivity::class.java)
            startActivity(intent)
        }

        showCompleteChapterButton.setOnClickListener {
            val intent = Intent(this,ArrayChapterActivity::class.java)
            intent.putExtra("isLearned","true")
            startActivity(intent)
        }
    }

    private fun firstStart(chapterManager:ChapterManager){
        if (chapterManager.isAnyChaptersExists()){
            val alertDialog = AlertDialog.Builder(this)
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER

            alertDialog.setTitle("Dodawanie rozdziałów")
            alertDialog.setView(editText)
            alertDialog.setMessage("Twoja lista rozdziałów jest pusta. Wpisz liczbe rozdziałów do nauczenia")
                .setPositiveButton("Dodaj"){_,_ ->
                    showToastForSaveChapter(chapterManager.createDefaultChapters(inputManager.createIntFromInput(editText)))
                }
                .setNegativeButton("Później") {dialog, _ ->
                    dialog.cancel()
                }
            alertDialog.show()
        } else {
            chapterManager.createArrays()
        }
    }

    private fun showToastForSaveChapter(status: ChapterManagerStatus){
        when(status){
            ChapterManagerStatus.StatusSingleSaveComplete -> Toast.makeText(applicationContext,"Rozdział zapisany", Toast.LENGTH_SHORT).show()
            ChapterManagerStatus.StatusAllSaveComplete -> Toast.makeText(applicationContext,"Rozdziały zostały dodane",Toast.LENGTH_SHORT).show()
            ChapterManagerStatus.StatusDataException -> Toast.makeText(applicationContext,"Brak dostępu do bazy danych", Toast.LENGTH_SHORT).show()
            ChapterManagerStatus.StatusNotDataToSave -> Toast.makeText(applicationContext,"Brak rozdziału do zapisania", Toast.LENGTH_LONG).show()
        }
    }

    private fun showToastForRandomChapter(chapter: String){
        Toast.makeText(this,"Wylosowany rozdział to: $chapter. Powodzenia ;)",Toast.LENGTH_SHORT).show()
    }

}



