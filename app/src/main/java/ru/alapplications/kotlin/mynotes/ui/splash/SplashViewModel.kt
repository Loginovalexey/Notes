package ru.alapplications.kotlin.mynotes.ui.splash

import kotlinx.coroutines.launch
import ru.alapplications.kotlin.mynotes.base.BaseViewModel
import ru.alapplications.kotlin.mynotes.model.NotesRepository
import ru.alapplications.kotlin.mynotes.model.errors.NoAuthException


class SplashViewModel(val notesRepository:NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: let {
            setError(NoAuthException())
        }
    }
}
