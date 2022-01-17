package ies.ua.inouttracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.model.FavStores
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.User
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponse_Stores: MutableLiveData<List<Store>> = MutableLiveData()
    val myResponse_Shoppings: MutableLiveData<List<Shopping>> = MutableLiveData()
    val myResponse_Users: MutableLiveData<List<User>> = MutableLiveData()
    val myResponse_SaveUser: MutableLiveData<Response<User>> = MutableLiveData()
    val myResponse_UserFavorites: MutableLiveData<Response<List<Int>>> = MutableLiveData()
    val myResponse_FavStores: MutableLiveData<Response<FavStores>> = MutableLiveData()

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

    fun getUsers(){
        viewModelScope.launch {
            val response = repository.getUsers()
            myResponse_Users.value = response
        }
    }

    fun saveUser(user: User){
        viewModelScope.launch {
            val response = repository.saveUser(user)
            myResponse_SaveUser.value = response
        }

    }

    fun getFavorites(user_id: Int){
        viewModelScope.launch {
            val response = repository.getFavorites(user_id)
            myResponse_UserFavorites.value = response
        }
    }

    fun saveFav(fav: FavStores){
        viewModelScope.launch {
            val response = repository.saveFav(fav)
            myResponse_FavStores.value = response
        }
    }
}