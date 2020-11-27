package ru.alapplications.kotlin.mynotes.ui.notescreen.viewmodel

import ru.alapplications.kotlin.mynotes.base.BaseViewState
import ru.alapplications.kotlin.mynotes.model.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)