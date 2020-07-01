package com.example.angielskinauka

import android.widget.EditText

class InputManager {

    fun createIntFromInput(editText: EditText): Int {
        return editText.text.toString().toInt()
    }
}