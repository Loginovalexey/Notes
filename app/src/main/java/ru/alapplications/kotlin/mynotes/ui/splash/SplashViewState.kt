package ru.alapplications.kotlin.mynotes.ui.splash

import ru.alapplications.kotlin.mynotes.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error:Throwable? = null):BaseViewState<Boolean?>(authenticated, error)