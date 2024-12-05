package com.example.budayakita.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budayakita.R

class OnboardingViewModel : ViewModel() {

    private val _onboardingItems = MutableLiveData<List<OnboardingItem>>()

    init {
        val items = listOf(
            OnboardingItem( R.drawable.foto1, R.string.title_menu_1, R.string.desc_menu_1),
            OnboardingItem( R.drawable.foto2, R.string.title_menu_2, R.string.desc_menu_2),
            OnboardingItem( R.drawable.foto3, R.string.title_menu_3, R.string.desc_menu_3)
        )
        _onboardingItems.value = items
    }

    val onboardingItems: LiveData<List<OnboardingItem>> = _onboardingItems
}