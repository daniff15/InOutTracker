package ies.ua.inouttracker.api

import ies.ua.inouttracker.ui.model.FavStores
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.User
import retrofit2.Response
import retrofit2.http.*

interface SimpleAPI {
    @GET("stores")
    suspend fun getStores(): List<Store>
    @GET("shoppings")
    suspend fun getShoppings(): List<Shopping>
    @GET("users")
    suspend fun getUsers(): List<User>
    @POST("users")
    suspend fun saveUser(@Body user: User) : Response<User>
    @GET("user/{id}/favorites")
    suspend fun getFavorites(@Path("id") user_id: Int): Response<List<Int>>
    @POST("add/favorite")
    suspend fun saveFav(@Body fav: FavStores): Response<FavStores>
    @HTTP(method = "DELETE", path = "remove/favorite", hasBody = true)
    suspend fun removeFav(@Body fav: FavStores)


}