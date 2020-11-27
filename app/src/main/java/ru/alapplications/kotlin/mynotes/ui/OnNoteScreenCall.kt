package ru.alapplications.kotlin.mynotes.ui

import ru.alapplications.kotlin.mynotes.model.entity.Note

interface OnNoteScreenCall {
    fun callNoteScreen(noteId: String?)
}
