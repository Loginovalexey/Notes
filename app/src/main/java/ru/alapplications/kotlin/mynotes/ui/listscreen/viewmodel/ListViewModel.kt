package ru.alapplications.kotlin.mynotes.ui.listscreen.viewmodel

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.alapplications.kotlin.mynotes.base.BaseViewModel
import ru.alapplications.kotlin.mynotes.model.NotesRepository
import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.model.entity.NoteResult


class ListViewModel(notesRepository: NotesRepository) : BaseViewModel<List<Note>?>() {
    private val notesChannel = notesRepository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when (it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        notesChannel.cancel()
        super.onCleared()
    }
}
