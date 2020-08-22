package com.example.angielskinauka

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val inputManager = InputManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chapterManager = ChapterManager(this)

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        createToolbar()
        firstStart(chapterManager)

        randomChapterButton.setOnClickListener{
            if(chapterManager.checkListLearnedSize()){
                val number = chapterManager.randomChapterNumber()
                resultTextView.text = number
                resultTextView.visibility = View.VISIBLE
            } else {
                Toast.makeText(this,"Brak rozdziału do wylosowania",Toast.LENGTH_SHORT).show()
            }
        }

        resultTextView.setOnClickListener {
            onClickResultsTextView(chapterManager,resultTextView.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.random_chapter,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val chapterManager = ChapterManager(this)
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

    private fun onClickResultsTextView(chapterManager: ChapterManager, number: String) {
            val alertDialog = AlertDialog.Builder(this)

            alertDialog.setTitle("Potwierdzenie")
            alertDialog.setMessage("Czy napewno nauczyłeś się tego rozdziału?")
                .setPositiveButton("Tak"){_,_ ->
                    chapterManager.updateChapterStatus(number.toInt())
                    updateData(chapterManager)
                    resultTextView.visibility = View.INVISIBLE
                    Toast.makeText(this,"Nauczyłeś się dziś nowego rozdziału! Brawo!!",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Jeszcze nie") {dialog, _ ->
                    dialog.cancel()
                }
            alertDialog.show()

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

    fun onClickAddress(view:View) {
        val address = "https://github.com/Lisek94/Angielski-nauka-apka"
        val startAddress = Intent(Intent.ACTION_VIEW, Uri.parse(address))
        startActivity(startAddress)
    }

    private fun createToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(MainActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuNotLearnedChapters -> {
            val intent = Intent(this,ArrayChapterActivity::class.java)
            startActivity(intent)
            }
            R.id.menuLearnedChapters -> {
                val intent = Intent(this,ArrayChapterActivity::class.java)
                intent.putExtra("isLearned","true")
                startActivity(intent)
            }
        }
        return true
    }

}



