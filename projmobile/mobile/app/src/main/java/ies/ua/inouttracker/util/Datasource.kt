package ies.ua.inouttracker.util

import ies.ua.inouttracker.MainActivity
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store

var stores: MutableList<Store> = mutableListOf()
var stores_logos: HashMap<String, Int> = hashMapOf()
var stores_dict: HashMap<Int, Store> = hashMapOf()
var shoppings: MutableList<Shopping> = mutableListOf()
var storesName: MutableList<String> = mutableListOf()
var shoppingsName: MutableList<String> = mutableListOf()
var favorite: MutableList<Store> = mutableListOf()

var SELF : MainActivity? = null


public class Datasource {
    fun getAllStores(): List<String> {
        return storesName
    }

    fun setAllStores(stores_list: List<Store>) {
        var count = 0
        stores = stores_list as MutableList<Store>
        for (store in stores)
            if (store.name != null && store.name !in storesName){
                stores_dict[count] = store
                storesName.add(store.name)
                count++
            }

    }

    fun getStoreCurrentCount(STORE: String): String {
        for (store in stores)
            if (store.name == STORE)
                return store.people_count.toString()
        return "0"
    }

    fun getStores(): MutableList<Store>{
        return stores
    }

    fun getStoreById(id: Int): Store? {
        return stores_dict[id]
    }

    fun getShoppings(): MutableList<Shopping>{
        return shoppings
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

    fun getStoreID(store: Store): Int {
        for (store_id in stores_dict)
            if (store_id.value == store)
                return store_id.key
        return -1
    }
    fun getSELF(): MainActivity? { return SELF }
    fun setSELF(self: MainActivity){ SELF = self}
    fun setStoreLogos(store_logos: HashMap<String, Int>){
        stores_logos = store_logos
    }

    fun getFavorite(): MutableList<Store> { return favorite }
    fun addFavorite(store: Store) { favorite.add(store) }
    fun removeFavorite(store: Store) { favorite.remove(store) }
    //TODO: Change to a dict with {id: shopping}
    fun getShoppingById(shopId: Int): String {
        for (shopping in getShoppings())
            if (shopping.id == shopId)
                return shopping.name
        return ""
    }

    fun getStoreLogo(storeName: String): Int? {
        if (storeName in stores_logos.keys)
            return stores_logos[storeName]
        return stores_logos[""]
    }

    fun loadFavorite(fav: MutableList<Store>) {
        favorite = fav
    }
}