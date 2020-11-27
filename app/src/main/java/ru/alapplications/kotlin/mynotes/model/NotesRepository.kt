package ru.alapplications.kotlin.mynotes.model

import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.model.provider.FireStoreDataProvider
import ru.alapplications.kotlin.mynotes.model.provider.DataProvider

//Синглтон
class NotesRepository (val dataProvider: DataProvider){
    fun getNotes() = dataProvider.subscribeToAllNotes()
    suspend fun saveNote(note:Note) = dataProvider.saveNote(note)
    suspend fun getNoteById(id:String) = dataProvider.getNoteById(id)
    suspend fun getCurrentUser() = dataProvider.getCurrentUser()
    suspend fun deleteNote(id: String) = dataProvider.deleteNote(id)
}