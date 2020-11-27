package ru.alapplications.kotlin.mynotes.ui.listscreen.viewmodel

import ru.alapplications.kotlin.mynotes.base.BaseViewState
import ru.alapplications.kotlin.mynotes.model.entity.Note

class ListViewState(val notes:List<Note>?=null, error:Throwable?=null):BaseViewState<List<Note>?>(notes,error)