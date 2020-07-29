package com.example.angielskinauka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
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
                    updateData(chapterManager)
                }
                .setNegativeButton("Anuluj") {dialog,_ ->
                    dialog.cancel()
                }
            builder.show()
        }

        randomChapterButton.setOnClickListener{
            if(chapterManager.checkListLearnedSize()){
                showToastForRandomChapter(chapterManager.randomChapterNumber())
                updateData(chapterManager)
            } else {
                Toast.makeText(this,"Brak rozdziału do wylosowania",Toast.LENGTH_SHORT).show()
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.random_chapter,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val chapterManager = ChapterManager(this)
        if(item.itemId == R.id.randomAB){
            if(chapterManager.checkListLearnedSize()){
                showToastForRandomChapter(chapterManager.randomChapterNumber())
                updateData(chapterManager)
            } else {
                Toast.makeText(this,"Brak rozdziału do wylosowania",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
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
                    updateData(chapterManager)
                }
                .setNegativeButton("Później") {dialog, _ ->
                    dialog.cancel()
                }
            alertDialog.show()
        }
        updateData(chapterManager)
    }

    private fun updateData(chapterManager: ChapterManager){
        chapterManager.createArrays()
        ProgressBarManager().setProgressBar(progressBar,percentTextView)
    }

    private fun showToastForSaveChapter(status: ChapterManagerStatus){
        when(status){
            ChapterManagerStatus.StatusSingleSaveComplete -> Toast.makeText(applicationContext,"Rozdział zapisany", Toast.LENGTH_SHORT).show()
            ChapterManagerStatus.StatusAllSaveComplete -> Toast.makeText(applicationContext,"Rozdziały zostały dodane",Toast.LENGTH_SHORT).show()
            ChapterManagerStatus.StatusDataException -> Toast.makeText(applicationContext,"Brak dostępu do bazy danych", Toast.LENGTH_SHORT).show()
            ChapterManagerStatus.StatusNotDataToSave -> Toast.makeText(applicationContext,"Brak rozdziału do zapisania", Toast.LENGTH_LONG).show()
            ChapterManagerStatus.StatusChapterExists -> Toast.makeText(applicationContext,"Rozdział już istnieje w bazie danych", Toast.LENGTH_LONG).show()
        }
    }

    private fun showToastForRandomChapter(chapter: String){
        Toast.makeText(this,"Wylosowany rozdział to: $chapter. Powodzenia ;)",Toast.LENGTH_SHORT).show()
    }

}



