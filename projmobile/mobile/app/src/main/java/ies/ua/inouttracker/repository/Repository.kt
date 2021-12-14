package ies.ua.inouttracker.repository

import ies.ua.inouttracker.api.RetrofitInstance
import ies.ua.inouttracker.ui.model.Store

class Repository {
    suspend fun getStores(): List<Store> {
        return RetrofitInstance.api.getStores()
    }
}