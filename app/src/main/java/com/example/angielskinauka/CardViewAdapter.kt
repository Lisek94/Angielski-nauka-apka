package com.example.angielskinauka

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(private val context: Context, private val isLearned:Boolean): RecyclerView.Adapter<MyViewHolder>() {

    private val chapterManager = ChapterManager(context)
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

        numberChapter.setOnClickListener {
            Toast.makeText(context,"Przytrzymaj dłużej, aby skasować rozdział",Toast.LENGTH_SHORT).show()
        }

        numberChapter.setOnLongClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Usuwanie rozdziału")
            alertDialog.setMessage("Czy na pewno chcesz usunąć rozdział?")
                .setPositiveButton("Tak"){_,_ ->
                    updateChapters(holder,position)
                }
                .setNegativeButton("Nie") {dialog, _ ->
                    dialog.cancel()
                }
            alertDialog.show()
            true
        }
    }

    private fun updateChapters(holder: MyViewHolder, position: Int){
        val titleChapter = holder.view.numberChapterTextView.text
        chapterManager.removeChapter(titleChapter.toString())
        notifyItemRemoved(position)
        if(isLearned){
            ChapterManager.arrayLearnedList.removeAt(position)
        } else {
            ChapterManager.arrayNotLearnedList.removeAt(position)
        }
        Toast.makeText(context,"Rozdział został skasowany",Toast.LENGTH_SHORT).show()
    }
}
class MyViewHolder(val view:View) : RecyclerView.ViewHolder(view)