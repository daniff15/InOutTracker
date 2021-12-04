package ies.ua.inouttracker.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StorePageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Store Page Fragment"
    }
    val text: LiveData<String> = _text
}