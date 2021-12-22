package ies.ua.inouttracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponse_Stores: MutableLiveData<List<Store>> = MutableLiveData()
    val myResponse_Shoppings: MutableLiveData<List<Shopping>> = MutableLiveData()

    fun getStores(){
        viewModelScope.launch {
            val response = repository.getStores()
            myResponse_Stores.value = response
        }
    }

    fun getShoppings(){
        viewModelScope.launch {
            val response = repository.getShoppings()
            myResponse_Shoppings.value = response
        }
    }
}