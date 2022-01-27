package ies.ua.inouttracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ies.ua.inouttracker.databinding.ActivityMainBinding
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.util.Datasource
import okhttp3.internal.notify

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var selfcont = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var first_control = true

        Datasource().setSELF(this)
        Datasource().setStoreLogos()

        updateDB()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // Create the Handler object (on the main thread by default)
        val handler = Handler()
        // Define the code block to be executed
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                updateDB()
                //Log.d("DEBUG", "User ID: "+Datasource().getCurrentUserId())
                if (first_control && Datasource().getFavorite().size == 0){
                    loadData()
                    if (Datasource().getFavorite().size != 0) first_control = false
                }

                notify_user()
                //Log.d("Handlers", "Called on main thread")
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000)
            }
        }
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode)

    }

    private fun loadData(){
        val sp: SharedPreferences = getPreferences(MODE_PRIVATE)
        val gson = Gson()
        val favorites: String? = sp.getString("favorites", null)
        val user: String? = sp.getString("loggedin", null)
        val user_id: String? = sp.getString("user_id", null)
        val notify: String? = sp.getString("notify", null)
        val percentage: String? = sp.getString("percentage", null)
        val custom: String? = sp.getString("custom", null)
        val empty: String? = sp.getString("empty", null)
        val full: String? = sp.getString("full", null)

        val type = object : TypeToken<String>() {}.type

        if (favorites != null) Datasource().loadFavorite(gson.fromJson(favorites, type))
        if (user != "\"\"" && user != null) Datasource().setLoggedIn(true, gson.fromJson(user, type))
        if (user_id != null){
            val id: String = gson.fromJson(user_id, type)
            id.toIntOrNull()?.let { Datasource().setCurrentUserId(it) }
        }
        if (notify != null){
            var notify: String = gson.fromJson(notify, type)
            Datasource().setNotified(notify.toString() == "true")
        }
        if (percentage != null){
            var percentage: String = gson.fromJson(percentage, type)
            percentage.toIntOrNull()?.let { Datasource().setPercentage(it) }
        }
        if (custom != null){
            var check: String = gson.fromJson(custom, type)
            Datasource().setCapacity_check(check.toString() == "true")
        }
        if (empty != null){
            var check: String = gson.fromJson(empty, type)
            Datasource().setIsEmpty_check(check.toString() == "true")
        }
        if (full != null){
            var check: String = gson.fromJson(full, type)
            Datasource().setIsFull_check(check.toString() == "true")
        }

        val self = Datasource().getSELF()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
        viewModel.getFavorites(Datasource().getCurrentUserId())
        viewModel.myResponse_UserFavorites.observe(self, { response ->
            var fav_ids = ""
            for (id in response.body()!!)
                fav_ids += "$id,"
            Datasource().loadFavorite(fav_ids)
        })
    }

    private fun notify_user() {
        if (Datasource().getFollowing().isNotEmpty() && Datasource().getNotified()) {
            for (store in Datasource().getFollowing().keys) {
                var title: String = ""
                var content: String = ""
                var notify = false
                if (Datasource().getIsEmpty_check() && store.people_count == 0) {
                    notify = true
                    title = "${store.name} is Empty"
                    content = "Now it's a good time to do your shopping"
                } else if (Datasource().getIsFull_check() && store.people_count >= store.max_capacity){
                    notify = true
                    title = "${store.name} is at Full Capacity"
                    content = "Maybe you should wait a little"
                } else if (Datasource().getCapacity_check() && store.people_count <= (store.max_capacity*(Datasource().getPercentage().toDouble()/100))){
                    notify = true
                    if (store.people_count == 0){
                        title = "${store.name} is Empty"
                        content = "Now it's a good time to do your shopping"
                    } else{
                        title = "There's only ${store.people_count} people at ${store.name}"
                        content = "Maybe it's a good time to go there"
                    }
                }
                if (notify){
                    var follow_not = NotificationCompat.Builder(applicationContext, "Notify")
                        .setSmallIcon(R.drawable.notification_bell)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            "Notify",
                            "Notify",
                            NotificationManager.IMPORTANCE_DEFAULT
                        ).apply {
                            description = "description"
                        }
                        // Register the channel with the system
                        val notificationManager: NotificationManager =
                            Datasource().getSELF()
                                ?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)

                        Datasource().removeFollowing(store)

                        with(applicationContext?.let { it1 -> NotificationManagerCompat.from(it1) }) {
                            // notificationId is a unique int for each notification that you must define
                            if (follow_not != null) {
                                this?.notify(0, follow_not.build())
                            }
                        }
                    }
                }

            }
        }
    }

    fun updateDB(){
        val self = Datasource().getSELF()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
        viewModel.getStores()
        viewModel.myResponse_Stores.observe(self, { response ->
            Datasource().setAllStores(response)
        })

        viewModel.getShoppings()
        viewModel.myResponse_Shoppings.observe(self, { response ->
            Datasource().setAllShoppings(response)
        })

    }
}