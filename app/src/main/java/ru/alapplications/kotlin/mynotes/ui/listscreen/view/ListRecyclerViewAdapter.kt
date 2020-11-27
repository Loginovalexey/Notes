package ru.alapplications.kotlin.mynotes.ui.listscreen.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import ru.alapplications.kotlin.common.getColorInt
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.model.entity.Note


class ListRecyclerViewAdapter (private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    //переопределяем setNotes(value)
    var notes:List<Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //with позволяет выполнить несколько операций над одним объектом, не повторяя его имени
        fun bind(note: Note) = with(itemView) {
            titleTextView.text = note.title
            noteTextView.text = note.text
            setBackgroundColor(note.color.getColorInt(itemView.context))
            itemView.setOnClickListener{onItemClickListener.onItemClick(note)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(notes[position])

}