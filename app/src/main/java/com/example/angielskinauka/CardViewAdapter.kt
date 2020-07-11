package com.example.angielskinauka

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(context: Context, private val isLearned:Boolean): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardViewAdapter = inflater.inflate(R.layout.card_view,parent,false)
        return MyViewHolder(cardViewAdapter)
    }

    override fun getItemCount(): Int {
        return if(isLearned){
            ChapterManager.arrayLearnedList.size
        } else {
            ChapterManager.arrayNotLearnedList.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val numberChapter = holder.view.numberChapterTextView
        if(isLearned){
            numberChapter.text = ChapterManager.arrayLearnedList[position]
        } else {
            numberChapter.text = ChapterManager.arrayNotLearnedList[position]
        }
    }
}
class MyViewHolder(val view:View) : RecyclerView.ViewHolder(view)