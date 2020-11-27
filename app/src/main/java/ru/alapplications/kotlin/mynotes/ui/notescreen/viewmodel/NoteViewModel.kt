package ru.alapplications.kotlin.mynotes.ui.notescreen.viewmodel

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import ru.alapplications.kotlin.mynotes.base.BaseViewModel
import ru.alapplications.kotlin.mynotes.model.NotesRepository
import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.model.entity.NoteResult

class NoteViewModel(val notesRepository: NotesRepository) : BaseViewModel<NoteData>() {

    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun loadNote(noteId: String) = launch {
        try {
            notesRepository.getNoteById(noteId).let {
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let {
                notesRepository.saveNote(it)
            }
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let { notesRepository.deleteNote(it.id) }
            setData(NoteData(isDeleted = true))
        } catch (e: Throwable) {
            setError(e)
        }
    }
}