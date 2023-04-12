package com.example.calendernote.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendernote.R
import com.example.calendernote.model.NoteDetail

class NoteAdapter(val c:Context,val noteList: ArrayList<NoteDetail>):
    RecyclerView.Adapter<NoteAdapter.noteViewHolder>()
{

    // 定義noteViewHolder，主要來綁定 參數與需要綁定的屬性ID
    inner class noteViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var noteName : TextView
        var noteDateSt : TextView
        var noteDateEd : TextView
        var noteLocation : TextView
        var noteDetail : TextView
    init {
        noteName = v.findViewById(R.id.noteName)
        noteDateSt = v.findViewById(R.id.noteDateStart)
        noteDateEd = v.findViewById(R.id.noteDateEnd)
        noteLocation = v.findViewById(R.id.noteLocation)
        noteDetail = v.findViewById(R.id.noteDetail)
    }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return noteViewHolder(v)
    }

    override fun getItemCount(): Int {
        // 返回Arraylist的長度
        return noteList.size

    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val noteItem = noteList[position]
        holder.noteName.text = noteItem.noteName
        holder.noteDateSt.text = noteItem.noteDateStart
        holder.noteDateEd.text = noteItem.noteDateEnd
        holder.noteLocation.text = noteItem.noteLocation
        holder.noteDetail.text = noteItem.note
    }
}