package com.example.angielskinauka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_chapters.*

class ArrayChapterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapters)
    }
    override fun onResume() {
        super.onResume()
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