package ies.ua.inouttracker.repository

import ies.ua.inouttracker.api.RetrofitInstance
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
}