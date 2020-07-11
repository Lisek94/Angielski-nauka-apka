package com.example.angielskinauka

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_chapters.*

class ArrayChapterActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        super.onStart()
        setContentView(R.layout.activity_chapters)
        val isLearned= intent.hasExtra("isLearned")

        if(isLearned){
            tittleActivity.text = "Lista nauczonych rozdziałów"
        } else {
            tittleActivity.text = "Lista nienauczonych rozdziałów"
        }

        recycle_view.layoutManager = GridLayoutManager(this,2)
        recycle_view.adapter = CardViewAdapter(this,isLearned)
    }
}