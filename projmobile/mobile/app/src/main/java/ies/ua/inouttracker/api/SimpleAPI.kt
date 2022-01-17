package ies.ua.inouttracker.api

import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SimpleAPI {
    @GET("stores")
    suspend fun getStores(): List<Store>
    @GET("shoppings")
    suspend fun getShoppings(): List<Shopping>
    @GET("users")
    suspend fun getUsers(): List<User>
    @POST("users")
    suspend fun saveUser(@Body user: User) : Response<User>

}