package ru.alapplications.kotlin.mynotes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.alapplications.kotlin.mynotes.model.entity.Note
import java.util.*

object NotesRepositoryStatic {

    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.WHITE
            ),
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.RED
            ),
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.BLUE
            ),
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.VIOLET
            ),
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.YELLOW
            ),
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.WHITE
            ),
            Note(
                UUID.randomUUID().toString(),
                "Заметка",
                "Содержание заметки",
                Note.Color.YELLOW
            ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.RED
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.BLUE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.VIOLET
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.YELLOW
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.YELLOW
        ),Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.RED
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.BLUE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.VIOLET
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.YELLOW
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Заметка",
            "Содержание заметки",
            Note.Color.YELLOW
        )
        )
    init {
        notesLiveData.value = notes;
    }
    fun getNotes():LiveData<List<Note>> = notesLiveData;

    private fun addOrReplace(note:Note){
        for (i in notes.indices){
            if (notes[i] == note){
                notes[i] = note;
                return
            }
        }
        notes.add(note)
    }
    fun saveNote(note:Note){
        addOrReplace(note)
        notesLiveData.value = notes
    }

}