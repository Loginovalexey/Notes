package ru.alapplications.kotlin.mynotes.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.ui.listscreen.view.ListFragment
import ru.alapplications.kotlin.mynotes.ui.notescreen.view.NoteFragment
import ru.alapplications.kotlin.mynotes.ui.splash.SplashFragment


class MainActivity : AppCompatActivity(), OnNoteScreenCall, OnListScreenCall,OnSplashScreenCall{

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainAcrivityFrameLayout, SplashFragment.getInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    override fun callNoteScreen(noteId: String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainAcrivityFrameLayout, NoteFragment.getInstance(noteId))
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

    override fun callListScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainAcrivityFrameLayout, ListFragment.getInstance())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

    override fun callSplashScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainAcrivityFrameLayout, SplashFragment.getInstance())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }
}