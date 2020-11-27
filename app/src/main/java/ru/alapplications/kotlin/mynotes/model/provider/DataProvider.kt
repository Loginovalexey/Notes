package ru.alapplications.kotlin.mynotes.model.provider

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.ReceiveChannel
import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.model.entity.NoteResult
import ru.alapplications.kotlin.mynotes.model.entity.User

interface DataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id:String):Note
    suspend fun saveNote(note: Note):Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}