package ru.alapplications.kotlin.mynotes.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.client.AuthUiInitProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.model.errors.NoAuthException
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment< S > : Fragment(), CoroutineScope {

    companion object{
        const val RC_SIGN = 4242
    }
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }
    private lateinit var dataJob: Job
    private lateinit var errorJob: Job
    abstract val viewModel: BaseViewModel<S>
    abstract val layoutRes: Int?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutRes?.let{return inflater.inflate(it, container, false)}
            ?:return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            viewModel.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob = launch {
            viewModel.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    abstract fun renderData(data: S)

    protected fun renderError(error: Throwable) {
        when(error){
            is NoAuthException -> startLogin()
            else -> error?.message?.let { message ->
                showError(message)
            }
        }

    }

    //Открываем экран авторизации в случае ошибки
    private fun startLogin() {
        //Определяем список провайдеров, через  которых будем авторизироваться, предоставляет FireBase
        val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.logo)
            .setTheme(R.style.LoginTheme)
            .setAvailableProviders(providers)
            .build()
        startActivityForResult(intent, RC_SIGN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN && resultCode != Activity.RESULT_OK){
            activity?.finish()
        }
    }

    protected fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}