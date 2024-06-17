package com.capstone.hay.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.hay.data.di.Injection
import com.capstone.hay.data.repository.ArticleRepository
import com.capstone.hay.data.repository.AuthRepository
import com.capstone.hay.view.home.HomeViewModel
import com.capstone.hay.view.login.LoginViewModel
import com.capstone.hay.view.news.NewsViewModel
import com.capstone.hay.view.profile.ProfileViewModel
import com.capstone.hay.view.register.RegisterViewModel
import com.capstone.hay.view.splash.SplashViewModel
import com.capstone.hay.view.verification.VerificationViewModel

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val articleRepository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(VerificationViewModel::class.java) -> {
                VerificationViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(articleRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.provideArticleRepository(context)
                ).also { INSTANCE = it }
            }
        }
    }
}
