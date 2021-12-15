package ies.ua.inouttracker.util

import ies.ua.inouttracker.MainActivity
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store

var stores: MutableList<Store> = mutableListOf()
var shoppings: MutableList<Shopping> = mutableListOf()
var storesName: MutableList<String> = mutableListOf()
var shoppingsName: MutableList<String> = mutableListOf()

var SELF : MainActivity? = null


public class Datasource {
    fun getAllStores(): List<String> {
        return storesName
    }

    fun setAllStores(stores_list: List<Store>) {
        stores = stores_list as MutableList<Store>
        for (store in stores)
            if (store.name != null && store.name !in storesName)
                storesName.add(store.name)
    }

    fun getStoreCurrentCount(STORE: String): String {
        for (store in stores)
            if (store.name == STORE)
                return store.people_count.toString()
        return "0"
    }

    fun getAllShoppings(): List<String> {
        return shoppingsName
    }

    fun setAllShoppings(response: List<Shopping>?) {
        shoppings = response as MutableList<Shopping>
        for (shopping in shoppings)
            if (shopping.name != null && shopping.name !in shoppingsName)
                shoppingsName.add(shopping.name)
    }

    fun getShoppingCurrentCount(SHOPPING: String): String {
        for (store in shoppings)
            if (store.name == SHOPPING)
                return store.people_count.toString()
        return "0"
    }

    fun getSELF(): MainActivity? { return SELF }
    fun setSELF(self: MainActivity){ SELF = self}
}