package com.example.budayakita.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budayakita.data.UserRepository
import com.example.budayakita.di.Injection
import com.example.budayakita.ui.auth.detail.DetailViewModel
import com.example.budayakita.ui.auth.login.LoginViewModel
import com.example.budayakita.ui.auth.otp.OtpViewModel
import com.example.budayakita.ui.auth.register.RegisterViewModel
import com.example.budayakita.ui.explore.ExploreViewModel
import com.example.budayakita.ui.glossary.GlossaryViewModel
import com.example.budayakita.ui.home.MainViewModel
import com.example.budayakita.ui.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(OtpViewModel::class.java) -> {
                OtpViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ExploreViewModel::class.java) ->{
                ExploreViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(GlossaryViewModel::class.java) -> {
                GlossaryViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context)
                ).also { instance = it }
            }
        }
    }
}
