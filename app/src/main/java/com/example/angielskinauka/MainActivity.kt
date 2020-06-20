package com.example.angielskinauka

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dBHelper = DataBaseHelper(this)
        val db = dBHelper.writableDatabase

        addChapterButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val editText = EditText(this)
            val isLearned = "false"
            editText.inputType = InputType.TYPE_CLASS_NUMBER

            builder.setView(editText)
            builder.setMessage("Wpisz numer rozdziału")
                .setPositiveButton("Zapisz") { _, _ ->
                    if(editText.length() < 1){
                        Toast.makeText(applicationContext,"Brak rozdziału do zapisania",Toast.LENGTH_LONG).show()
                    } else {
                        val value = ContentValues()
                        value.put("title",editText.text.toString())
                        value.put("isLearned",isLearned)
                        db.insertOrThrow(TableInfo.TABLE_NAME,null,value)
                        Toast.makeText(applicationContext,"Rozdział zapisany",Toast.LENGTH_SHORT).show()
                    }

                }
                .setNegativeButton("Anuluj") {dialog,_ ->
                    dialog.cancel()
                }
            builder.show()
        }
    }
}

fun firstStart(db: SQLiteDatabase, context: Context){
    val cursor = db.query(TableInfo.TABLE_NAME,null,null,null,null,null,null)
    if (cursor.count == 0){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Dodawanie rozdziałów")

    }
}

