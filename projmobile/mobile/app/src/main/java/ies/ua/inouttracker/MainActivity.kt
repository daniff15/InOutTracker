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

        Datasource().setSELF(this)
        Datasource().setStoreLogos(hashMapOf(
            "FNAC" to R.mipmap.fnac_logo,
            "Mi Store" to R.mipmap.mi_logo,
            "Sport Zone" to R.mipmap.sport_zone_logo,
            "" to R.mipmap.no_image
        ))


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

    private fun notify_user() {
        //TODO: fix this function
        if (Datasource().getFollowing().isNotEmpty()) {
            for (store in Datasource().getFollowing().keys) {
                if (store.people_count == store.max_capacity) {
                    var follow_not = NotificationCompat.Builder(applicationContext, "Notify")
                        .setSmallIcon(R.drawable.notification_bell)
                        .setContentTitle("Test Notification")
                        .setContentText("Some big test to describe what's happening")
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
                    }

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