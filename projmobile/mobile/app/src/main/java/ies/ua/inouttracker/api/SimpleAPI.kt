package ies.ua.inouttracker.api

import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store
import retrofit2.http.GET

interface SimpleAPI {
    @GET("stores")
    suspend fun getStores(): List<Store>
    @GET("shoppings")
    suspend fun getShoppings(): List<Shopping>
}