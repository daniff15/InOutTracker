package ies.ua.inouttracker.util

import android.util.Log
import ies.ua.inouttracker.ui.model.Store

var stores: MutableList<Store> = mutableListOf()
var storesName: MutableList<String> = mutableListOf()

public class Datasource {


    fun getAllStores(): List<String> {
        return storesName
    }

    fun setAllStores(stores: List<Store>) {
        for (store in stores)
            if (store.name != null)
                storesName.add(store.name)
    }

}