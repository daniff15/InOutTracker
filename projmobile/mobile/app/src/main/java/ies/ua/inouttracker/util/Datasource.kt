package ies.ua.inouttracker.util

import android.util.Log
import ies.ua.inouttracker.MainActivity
import ies.ua.inouttracker.R
import ies.ua.inouttracker.ui.model.Shopping
import ies.ua.inouttracker.ui.model.Store

var stores: MutableList<Store> = mutableListOf()
var stores_logos: HashMap<String, Int> = hashMapOf()
var stores_dict: HashMap<Int, Store> = hashMapOf()
var shoppings: MutableList<Shopping> = mutableListOf()
var storesName: MutableList<String> = mutableListOf()
var shoppingsName: MutableList<String> = mutableListOf()
var favorite: MutableSet<Store> = mutableSetOf()
var following: HashMap<Store, Int> = hashMapOf()
var current_store: Store = Store(0,0, "", "", "", 0, 0, 0)
var loggedin: Boolean = false
var current_user: String = ""
var current_user_id: Int = -1
var shopping_stores: HashMap<Int, MutableList<Store>> = hashMapOf()
var getNotified: Boolean = false

var capacity_check: Boolean = true
var percentage: Int = 50
var isEmpty_check: Boolean = false
var isFull_check: Boolean = false

var SELF : MainActivity? = null


public class Datasource {
    fun getIsFull_check(): Boolean {
        return isFull_check
    }
    fun setIsFull_check(b: Boolean){
        isFull_check = b
    }

    fun getIsEmpty_check(): Boolean {
        return isEmpty_check
    }
    fun setIsEmpty_check(b: Boolean){
        isEmpty_check = b
    }

    fun getCapacity_check(): Boolean {
        return capacity_check
    }
    fun setCapacity_check(b: Boolean){
        capacity_check = b
    }

    fun getPercentage(): Int {
        return percentage
    }
    fun setPercentage(percent: Int){
        percentage = percent
    }

    fun getNotified(): Boolean {
        return getNotified
    }

    fun setNotified(b: Boolean){
        getNotified = b
    }

    fun getAllStores(): List<String> {
        return storesName
    }

    fun setAllStores(stores_list: List<Store>) {
        stores = stores_list as MutableList<Store>
        val new_shopping_stores = mutableMapOf<Int, MutableList<Store>>() as HashMap<Int, MutableList<Store>>
        for (store in stores) {
            if (store.name != null && store.name !in storesName) {
                storesName.add(store.name)
            }
            stores_dict[store.id] = store
            if (store.shop_id in new_shopping_stores){
                new_shopping_stores[store.shop_id]?.add(store)
            } else{
                new_shopping_stores[store.shop_id] = mutableListOf(store)
            }
        }
        shopping_stores = new_shopping_stores

    }

    fun getShoppingStores(mall_id: Int): MutableList<Store>? {
        return shopping_stores[mall_id]
    }

    fun getShoppingId(name: String): Int {
        for(shopping in shoppings){
            if (shopping.name == name) return shopping.id
        }
        return -1
    }

    fun getStoreFromMallAndStoreName (mall: String, store_name: String): Store {
        val mall_id = getShoppingId(mall)
        for (store in stores)
            if (store_name == store.name && store.shop_id == mall_id) return store
        return Store(-1, -1, "Not Found", "00h00", "00h00", 0, 0, 0)
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
    fun setStoreLogos(){
        stores_logos = hashMapOf(
            "FNAC" to R.mipmap.fnac_logo,
            "Mi Store" to R.mipmap.mi_logo,
            "Sport Zone" to R.mipmap.sport_zone_logo,
            "Levi's" to R.mipmap.levis_logo,
            "Zara" to R.mipmap.zara_logo,
            "Pull & Bear" to R.mipmap.pull_bear_logo,
            "Bershka" to R.mipmap.bershka_logo,
            "Springfield" to R.mipmap.springfield_logo,
            "Claire's" to R.mipmap.claires_logo,
            "Body Shop" to R.mipmap.body_shop_logo,
            "Bimba Y Lola" to R.mipmap.bimba_lola_logo,
            "Boutique dos Rel??gios" to R.mipmap.boutique_logo,
            "Calzedonia" to R.mipmap.calzedonia_logo,
            "Decenio" to R.mipmap.decenio_logo,
            "Quebramar" to R.mipmap.quebramar_logo,
            "Tiffosi" to R.mipmap.tiffosi_logo,
            "Worten" to R.mipmap.worten_logo,
            "Auchan" to R.mipmap.auchan_logo,
            "C&A" to R.mipmap.ca_logo,
            "H&M" to R.mipmap.hm_logo,
            "Cortefiel" to R.mipmap.cortefiel_logo,
            "Lefties" to R.mipmap.lefties_logo,
            "New Yorker" to R.mipmap.newyorker_logo,
            "" to R.mipmap.no_image
        )
    }

    fun getFavorite(): MutableSet<Store> { return favorite }
    fun addFavorite(store: Store) { favorite.add(store) }
    fun removeFavorite(store: Store) { favorite.remove(store) }
    fun getShoppingById(shopId: Int): String {
        for (shopping in getShoppings())
            if (shopping.id == shopId)
                return shopping.name
        return ""
    }


    fun getFollowing(): HashMap<Store, Int>{
        return following
    }
    fun addFollowing(store: Store, percentage: Int){
        following[store] = percentage
    }
    fun removeFollowing(store: Store){
        following.remove(store)
    }

    fun getStoreLogo(storeName: String): Int? {
        if (storeName in stores_logos.keys)
            return stores_logos[storeName]
        return stores_logos[""]
    }

    fun getFavoriteID(): String{
        var list_ids = ""
        for (store in getFavorite())
            list_ids += store.id.toString() + ","
        return list_ids
    }
    fun listFavoriteID(): Set<Int> {
        var list_ids = mutableListOf<Int>()
        for (store in getFavorite())
            list_ids.add(store.id)
        return list_ids.toSet()
    }

    fun loadFavorite(fav_ids: String) {
        var ids = fav_ids.split(',')
        for (id in ids){
            var id_int = id.toIntOrNull()
            if (id_int != null) getStoreById(id_int)?.let { addFavorite(it) }
        }
    }

    fun setCurrentStore(store: Store){
        current_store = store
    }
    fun getCurrentStore(): Store {
        return current_store
    }

    fun setLoggedIn(b: Boolean, username: String, user_id: Int = -1) {
        loggedin = b
        current_user = username
        if (user_id != -1) current_user_id = user_id
    }

    fun isLoggedIn(): Boolean {
        return loggedin
    }

    fun getCurrentUser(): String {
        return current_user
    }

    fun getCurrentUserId(): Int {
        return current_user_id
    }

    fun setCurrentUserId(id: Int) {
        current_user_id = id
    }

    fun getStoreCurrentWaiting(STORE: String): String {
        for (store in stores)
            if (store.name == STORE)
                return store.waiting.toString()
        return "0"
    }
}