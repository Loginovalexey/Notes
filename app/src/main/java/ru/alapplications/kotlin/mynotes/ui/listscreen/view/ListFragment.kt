package ru.alapplications.kotlin.mynotes.ui.listscreen.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.base.BaseFragment
import ru.alapplications.kotlin.mynotes.model.entity.Note
import ru.alapplications.kotlin.mynotes.ui.OnNoteScreenCall
import ru.alapplications.kotlin.mynotes.ui.OnSplashScreenCall
import ru.alapplications.kotlin.mynotes.ui.listscreen.viewmodel.ListViewModel
import ru.alapplications.kotlin.mynotes.ui.listscreen.viewmodel.ListViewState

class ListFragment : BaseFragment<List<Note>?>() {
    companion object {
        fun getInstance() = ListFragment()
    }

    //Ленивая инициализация viewModel
    //Указываем функцию, которая будет вызвана при первом использовании viewModel
    //В последующем будет использоваться созданные объект
    override val viewModel: ListViewModel by viewModel()

    override val layoutRes = R.layout.fragment_list

    //Отложенная инициализация - для переменных, которые не могут принимать значение null
    //Используется, когда в объявлении переменной еще неизвестно значение
    private lateinit var adapter: ListRecyclerViewAdapter
    private lateinit var onNoteScreenCall: OnNoteScreenCall
    private lateinit var onSplashScreenCall: OnSplashScreenCall

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNoteScreenCall = context as OnNoteScreenCall
        onSplashScreenCall = context as OnSplashScreenCall
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        listRecyclerView.layoutManager = GridLayoutManager(activity, 2);
        //object : создание анонимного экземпляра класса
        adapter =
            ListRecyclerViewAdapter(object : ListRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(note: Note) {
                    onNoteScreenCall.callNoteScreen(note.id)
                }
            })
        listRecyclerView.adapter = adapter
        fab.setOnClickListener { onNoteScreenCall.callNoteScreen(null) }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> false
        }

    private fun showLogoutDialog() {
        activity!!.supportFragmentManager.findFragmentByTag(LogoutDialog.TAG)
            ?: LogoutDialog.createInstance {
                onLogout()
            }.show(activity!!.supportFragmentManager, LogoutDialog.TAG)
    }

    fun onLogout() {

        AuthUI.getInstance()
            .signOut(activity!!)
            .addOnCompleteListener {
                onSplashScreenCall.callSplashScreen()
            }

    }
}
