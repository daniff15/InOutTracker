package ies.ua.inouttracker.repository

import android.util.Log
import ies.ua.inouttracker.api.RetrofitInstance
import ies.ua.inouttracker.ui.model.FavStores
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.User
import retrofit2.Response

class Repository {
    suspend fun getStores(): List<Store> {
        return RetrofitInstance.api.getStores()
    }
    suspend fun getShoppings(): List<Shopping>{
        return RetrofitInstance.api.getShoppings()
    }
    suspend fun getUsers(): List<User>{
        return RetrofitInstance.api.getUsers()
    }
    suspend fun saveUser(user: User): Response<User>{
        return RetrofitInstance.api.saveUser(user)
    }
    suspend fun getFavorites(user_id: Int): Response<List<Int>> {
        return RetrofitInstance.api.getFavorites(user_id)
    }
    suspend fun saveFav(fav: FavStores): Response<FavStores>{
        return RetrofitInstance.api.saveFav(fav)
    }
}