package com.example.angielskinauka

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
            editText.inputType = InputType.TYPE_CLASS_NUMBER

            builder.setView(editText)
            builder.setMessage("Wpisz numer rozdziału")
                .setPositiveButton("Zapisz") { _, _ ->
                    if(editText.length() < 1){
                        editText.hint = "Wpisz rozdział"
                    } else {
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
