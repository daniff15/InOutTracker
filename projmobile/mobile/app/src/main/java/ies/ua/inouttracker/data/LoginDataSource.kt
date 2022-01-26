package ies.ua.inouttracker.data

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.data.model.LoggedInUser
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.model.FavStores
import ies.ua.inouttracker.ui.model.User
import ies.ua.inouttracker.util.Datasource
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            var ret = check_user_in_db(username)
            if(ret.first){
                if(password == ret.second.password){
                    val user = LoggedInUser(java.util.UUID.randomUUID().toString(), " " + ret.second.name)
                    Datasource().setCurrentUserId(ret.second.id)
                    Datasource().setLoggedIn(true, username, ret.second.id)
                    lateinit var viewModel: MainViewModel
                    val self = Datasource().getSELF()
                    val repository = Repository()
                    val viewModelFactory = MainViewModelFactory(repository)
                    //Add Cached Favorites to DB
                    viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                    for (favorite in Datasource().getFavorite()){
                        viewModel.saveFav(FavStores(ret.second.id, favorite.id))
                        viewModel.myResponse_FavStores.observe(self, { response ->
                        })
                    }
                    //Get Favorites from the DB
                    viewModel.getFavorites(ret.second.id)
                    viewModel.myResponse_UserFavorites.observe(self, { response ->
                        for (shop_id in response.body()!!){
                            if (Datasource().getStoreById(shop_id) !in Datasource().getFavorite())
                                Datasource().getStoreById(shop_id)
                                    ?.let { Datasource().addFavorite(it) }
                        }
                    })


                    return Result.Success(user)
                }
            } else{
                lateinit var viewModel: MainViewModel
                val self = Datasource().getSELF()
                val repository = Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                val user_id = getId()
                Datasource().setCurrentUserId(user_id)
                Datasource().setLoggedIn(true, username, user_id)
                viewModel.saveUser(User(user_id,0, username, username, password))
                viewModel.myResponse_SaveUser.observe(self, { response ->

                })
                for (favorite in Datasource().getFavorite()){
                    viewModel.saveFav(FavStores(user_id, favorite.id))
                    viewModel.myResponse_FavStores.observe(self, { response ->
                    })
                }
            }
            return Result.Error(IOException("Error logging in"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {

    }

    fun check_user_in_db(username: String): Pair<Boolean, User> {
        lateinit var viewModel: MainViewModel
        val self = Datasource().getSELF()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        var ret = Pair(false, User(-1, 0, "", "", ""))
        viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
        viewModel.getUsers()
        viewModel.myResponse_Users.observe(self, { response ->
            for (user in response){
                if (user.username == username) ret = Pair(true, user)
            }
        })
        return ret
    }

    fun getId(): Int {
        lateinit var viewModel: MainViewModel
        val self = Datasource().getSELF()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        var max_id = 0

        var finish = false

        viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
        viewModel.getUsers()
        viewModel.myResponse_Users.observe(self, { response ->
            for (user in response){
                if (user.id > max_id) max_id = user.id
            }
            finish = true
        })

        return max_id + 1
    }
}