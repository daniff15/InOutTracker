package ies.ua.inouttracker.ui.login

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.gson.Gson
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.databinding.FragmentLoginBinding

import ies.ua.inouttracker.R
import ies.ua.inouttracker.data.model.LoggedInUser
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.model.FavStores
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.User
import ies.ua.inouttracker.util.Datasource
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(view, username, password, loadingProgressBar)
            /*
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )*/
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun login(
        view: View,
        username: String,
        password: String,
        loadingProgressBar: ProgressBar
    ){
        val appContext = context?.applicationContext ?: return
        var message = ""
        var put = true
        var favorites_added: MutableList<Store> = mutableListOf()

        Log.d("UsernamePassword",username + password)

        lateinit var viewModel: MainViewModel
        val self = Datasource().getSELF()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
        viewModel.getUsers()
        viewModel.myResponse_Users.observe(self, { response ->
            Log.d("LOGIN", "Start")
            var ret: Pair<Boolean, User> = Pair(false, User(-1, 0, "", "", ""))
            for (user in response){
                if (user.username == username){
                    ret = Pair(true, user)
                    if(password == ret.second.password){
                        val user = LoggedInUser(java.util.UUID.randomUUID().toString(), " " + ret.second.name)
                        Datasource().setCurrentUserId(ret.second.id)
                        Datasource().setLoggedIn(true, username, ret.second.id)
                        lateinit var viewModel: MainViewModel
                        val self = Datasource().getSELF()
                        val repository = Repository()
                        val viewModelFactory = MainViewModelFactory(repository)
                        viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!

                        //Get Favorites from the DB
                        viewModel.getFavorites(ret.second.id)
                        viewModel.myResponse_UserFavorites.observe(self, { response ->
                            for (shop_id in response.body()!!){
                                Datasource().getStoreById(shop_id)?.let { favorites_added.add(it) }
                                if (Datasource().getStoreById(shop_id) !in Datasource().getFavorite())
                                    Datasource().getStoreById(shop_id)
                                        ?.let { Datasource().addFavorite(it) }
                            }
                        })

                        //Add Cached Favorites to DB
                        for (favorite in Datasource().getFavorite()){
                            if (favorite !in favorites_added) {
                                favorites_added.add(favorite)
                                viewModel.saveFav(FavStores(ret.second.id, favorite.id))
                                viewModel.myResponse_FavStores.observe(self, { response ->
                                })
                            }
                        }

                        message = "Welcome! " + ret.second.username
                    } else {
                        //Password is wrong
                        message = "Login Failed! Wrong Password"
                    }
                    loadingProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
                    return@observe
                }
            }
            if (!ret.first){
                Log.d("LOGIN", "Not Found")
                var user_id = -1
                viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                viewModel.getUsers()
                viewModel.myResponse_Users.observe(self, { response ->
                    if (put){
                        for (user in response){
                            if (user.id > user_id) user_id = user.id
                        }
                        user_id += 1
                        Datasource().setCurrentUserId(user_id)
                    }

                    lateinit var viewModel: MainViewModel
                    val self = Datasource().getSELF()
                    val repository = Repository()
                    val viewModelFactory = MainViewModelFactory(repository)
                    viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                    val user = LoggedInUser(java.util.UUID.randomUUID().toString(), " $username")
                    Datasource().setLoggedIn(true, username)
                    if (put) {
                        viewModel.saveUser(User(user_id,0, username, username, password))
                        put = false
                    }
                    viewModel.myResponse_SaveUser.observe(self, { response ->

                    })
                    for (favorite in Datasource().getFavorite()){
                        if (favorite !in favorites_added){
                            favorites_added.add(favorite)
                            viewModel.saveFav(FavStores(user_id, favorite.id))
                            viewModel.myResponse_FavStores.observe(self, { response ->
                            })
                        }
                    }
                })
            }
        })
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_navigation_notifications)
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            val username: String = gson.toJson(Datasource().getCurrentUser())
            val id: String? = gson.toJson(Datasource().getCurrentUserId())

            editor.putString("loggedin", username)
            editor.putString("user_id", id)
            editor.commit()

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        //Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}