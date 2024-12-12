package com.example.budayakita.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> = _userSession

    private val _isDarkModeEnabled = MutableLiveData<Boolean>()
    val isDarkModeEnabled: LiveData<Boolean> = _isDarkModeEnabled

    fun getSession() {
        viewModelScope.launch {
            userRepository.getSession().collect { userModel ->
                _userSession.postValue(userModel)
            }
        }
    }

    fun toggleDarkMode(isEnabled: Boolean) {
        viewModelScope.launch {
            userRepository.saveDarkModePreference(isEnabled)  // Simpan perubahan ke dalam repository
            _isDarkModeEnabled.postValue(isEnabled)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _userSession.postValue(UserModel("", false))  // Reset user session
        }
    }

    fun loadDarkModePreference() {
        viewModelScope.launch {
            userRepository.getDarkModePreference().collect { isDarkMode ->
                _isDarkModeEnabled.postValue(isDarkMode)  // Load preference dari repository
            }
        }
    }
}
