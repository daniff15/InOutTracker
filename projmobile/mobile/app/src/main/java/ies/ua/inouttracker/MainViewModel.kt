package ies.ua.inouttracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.model.Store
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponse: MutableLiveData<List<Store>> = MutableLiveData()

    fun getStores(){
        viewModelScope.launch {
            val response = repository.getStores()
            myResponse.value = response
        }
    }
}