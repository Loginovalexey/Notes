package ru.alapplications.kotlin.mynotes.ui.listscreen.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.alapplications.kotlin.mynotes.model.NotesRepository
import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.model.entity.NoteResult

class ListViewModelTest{
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()

    private lateinit var viewModel: ListViewModel

    @Before
    fun setup() {
        clearAllMocks()
        every { mockRepository.getNotes() } returns notesLiveData
        viewModel = ListViewModel(mockRepository)
    }

    @Test
    fun `should call getNotes once`() {
        verify(exactly = 1) { mockRepository.getNotes() }
    }

    @Test
    fun `should return notes`(){
        var result: List<Note>? = null
        val testData = listOf(Note("1"), Note("2"))
        viewModel.viewStateLiveData.observeForever{
            result = it?.data
        }
        notesLiveData.value = NoteResult.Success(testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should return error`(){
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.viewStateLiveData.observeForever{
            result = it?.error
        }
        notesLiveData.value = NoteResult.Error(error = testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should remove observer`(){
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }


}