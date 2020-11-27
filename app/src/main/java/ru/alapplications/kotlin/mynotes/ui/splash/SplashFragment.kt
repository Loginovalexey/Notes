package ru.alapplications.kotlin.mynotes.ui.splash

import android.content.Context
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.base.BaseFragment
import ru.alapplications.kotlin.mynotes.ui.OnListScreenCall
import org.koin.android.viewmodel.ext.android.viewModel


class SplashFragment : BaseFragment<Boolean?>() {
    private lateinit var onListScreenCall: OnListScreenCall

    companion object {
        fun getInstance() = SplashFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onListScreenCall = context as OnListScreenCall
    }

    //Ленивая инициализация viewModel
    //Указываем функцию, которая будет вызвана при первом использовании viewModel
    //В последующем будет использоваться созданные объект
    override val viewModel: SplashViewModel by viewModel()
    override val layoutRes = R.layout.fragment_splash;

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }


    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startListScreen()
        }
    }

    private fun startListScreen() {
        onListScreenCall.callListScreen()
    }
}
