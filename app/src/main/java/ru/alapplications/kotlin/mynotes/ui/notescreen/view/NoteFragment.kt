package ru.alapplications.kotlin.mynotes.ui.notescreen.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_list.toolbar
import kotlinx.android.synthetic.main.fragment_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.alapplications.kotlin.common.getColorInt
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.base.BaseFragment
import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.ui.notescreen.viewmodel.NoteData
import ru.alapplications.kotlin.mynotes.ui.notescreen.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class NoteFragment : BaseFragment<NoteData>() {
    //статические объекты и функции
    companion object {
        //статическая константа для обозначения аргумента передаваемой во фрагмент заметки
        private val EXTRA_NOTE = NoteFragment::class.java.name + "extra.NOTE"
        private const val DATA_FORMAT = "dd.MM.yy HH.mm"
        private var color: Note.Color = Note.Color.WHITE

        @JvmStatic
        fun getInstance(id: String?) =
            NoteFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_NOTE, id)
                }
            }
    }

    private var note: Note? = null
    private var noteId: String? = null

    override val viewModel: NoteViewModel by viewModel()

    override val layoutRes = R.layout.fragment_note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //если есть аргументы, то выполняем
        arguments?.let {
            noteId = it.getString(EXTRA_NOTE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true)
        noteId?.let { id ->
            viewModel.loadNote(id)
        }
        initView()
    }


    override fun renderData(data: NoteData) {
        if (data.isDeleted) (activity as AppCompatActivity).getSupportFragmentManager()!!
            .popBackStack()
        this.note = data.note
        initView()
    }

    private fun initView() {
        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        note?.let { note ->
            et_title.setText(note.title)
            et_body.setText(note.text)
            et_title.setSelection(et_title.text?.length ?: 0)
            et_body.setSelection(et_body.text?.length ?: 0)
            SimpleDateFormat(DATA_FORMAT, Locale.getDefault()).format(note.lastChanged)
            toolbar.setBackgroundColor(note.color.getColorInt(activity!!))
        } ?: let {
            (activity as AppCompatActivity?)?.supportActionBar?.title =
                getString(R.string.new_note_title)
        }
        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)

        colorPicker.onColorClickListener = {
            color = it
            toolbar.setBackgroundColor(color.getColorInt(activity!!))
            saveNote()
        }

    }

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    private fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return
        //copy - функция Data - классов в Котлин, нужна для создания нового объекта с изменением свойств
        //то есть оставляем UUID, все остальное заменяем
        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date(),
            color = color
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())
        note?.let {
            viewModel.save(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePallete().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        activity!!.onBackPressed()
    }

    fun togglePallete() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    fun deleteNote() {
        AlertDialog.Builder(activity)
            .setMessage(getString(R.string.note_delete_message))
            .setPositiveButton(R.string.note_delete_ok) { dialog, which -> viewModel.deleteNote() }
            .setNegativeButton(R.string.note_delete_cancel) { dialog, which -> dialog.dismiss() }
            .show()
    }

}