package ru.alapplications.kotlin.mynotes.model.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.alapplications.kotlin.mynotes.model.NotesRepository
import ru.alapplications.kotlin.mynotes.model.provider.DataProvider
import ru.alapplications.kotlin.mynotes.model.provider.FireStoreDataProvider
import ru.alapplications.kotlin.mynotes.ui.listscreen.viewmodel.ListViewModel
import ru.alapplications.kotlin.mynotes.ui.notescreen.viewmodel.NoteViewModel
import ru.alapplications.kotlin.mynotes.ui.splash.SplashViewModel

val appModule =  module {
    single {FirebaseAuth.getInstance()}
    single {FirebaseFirestore.getInstance()}
    single <DataProvider> {FireStoreDataProvider(get(),get())}
    single { NotesRepository(get()) }
}

val splashModule = module{
    viewModel{ SplashViewModel(get()) }
}

val listModule = module{
    viewModel{ ListViewModel(get()) }
}

val noteModule = module{
    viewModel{ NoteViewModel(get()) }
}