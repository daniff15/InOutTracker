package ies.ua.inouttracker.data

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.data.model.LoggedInUser
import ies.ua.inouttracker.repository.Repository
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
                    return Result.Success(user)
                }
            } else{
                lateinit var viewModel: MainViewModel
                val self = Datasource().getSELF()
                val repository = Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                viewModel.saveUser(User(77,0, username, username, password))
                viewModel.myResponse_SaveUser.observe(self, { response ->
                })
            }
            return Result.Error(IOException("Error logging in"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
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
}