package ru.alapplications.kotlin.mynotes.ui

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.alapplications.kotlin.mynotes.model.di.appModule
import ru.alapplications.kotlin.mynotes.model.di.listModule
import ru.alapplications.kotlin.mynotes.model.di.noteModule
import ru.alapplications.kotlin.mynotes.model.di.splashModule

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, listModule,noteModule))
    }

}