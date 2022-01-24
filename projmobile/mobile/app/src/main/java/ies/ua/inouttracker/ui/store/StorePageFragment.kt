package ies.ua.inouttracker.ui.store

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentStorePageBinding
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.dashboard.DashboardViewModel
import ies.ua.inouttracker.ui.model.Dialog
import ies.ua.inouttracker.ui.model.FavStores
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.User
import ies.ua.inouttracker.util.Datasource
import java.net.DatagramSocket

class StorePageFragment() : Fragment() {

    private lateinit var storePageViewModel: StorePageViewModel
    private var _binding: FragmentStorePageBinding? = null
    private var store = Datasource().getCurrentStore()
    private lateinit var viewModel: MainViewModel
    private var self = this

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storePageViewModel =
            ViewModelProvider(this).get(StorePageViewModel::class.java)

        _binding = FragmentStorePageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val store_name = view.findViewById<TextView>(R.id.store_page_name)
        val store_logo = view.findViewById<ImageView>(R.id.store_page_logo)
        val follow = view.findViewById<Button>(R.id.follow_button)
        val current = view.findViewById<TextView>(R.id.mall_count_current_count)
        val waiting = view.findViewById<TextView>(R.id.waiting_count)
        val max = view.findViewById<TextView>(R.id.mall_count_current_count2)
        val info_button = view.findViewById<ImageButton>(R.id.info)
        val opening_hours = view.findViewById<TextView>(R.id.opening_hours)

        Datasource().getStoreLogo(store.name)?.let { store_logo.setImageResource(it) }
        store_name.text = store.name
        current.text = store.people_count.toString()
        max.text = store.max_capacity.toString()
        opening_hours.text = store.opening_time + " - " + store.closing_time

        if (store in Datasource().getFollowing().keys) follow.text = "Unfollow"

        val fav = view?.findViewById<ImageButton>(R.id.favorite)

        if (store in Datasource().getFavorite()) {
            fav.setImageResource(R.mipmap.hearton)
            fav.tag = R.mipmap.hearton
        }

        follow.setOnClickListener {
            if (Datasource().getNotified()) {
                if (follow.text == "Follow") {
                    Datasource().addFollowing(store, 1)
                    follow.text = "Unfollow"

                    Toast.makeText(
                        context,
                        "You are now following this store",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (follow.text == "Unfollow") {
                    Datasource().removeFollowing(store)
                    follow.text = "Follow"

                    Toast.makeText(
                        context,
                        "You stopped following this store",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                var dialog = Dialog("Turn on Push Notifications", "In order to get notifications for the stores you follow you have to turn your notifications on", "Ok", DialogInterface.OnClickListener { dialog, which ->
                    Navigation.findNavController(view).navigate(R.id.action_storePageFragment_to_navigation_notifications)
                }, "Cancel", DialogInterface.OnClickListener { dialog, which ->
                })
                dialog.isCancelable = false
                dialog.show(parentFragmentManager, "Turn on Notifications")
            }
        }

        fav.setOnClickListener {
            if (fav.tag == R.mipmap.hearton) {
                fav.setImageResource(R.mipmap.heartoff)
                fav.tag = R.mipmap.heartoff
                Datasource().removeFavorite(store)
                Toast.makeText(
                    context,
                    "Store removed from favorites",
                    Toast.LENGTH_SHORT
                ).show()

                lateinit var viewModel: MainViewModel
                val self = Datasource().getSELF()
                val repository = Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                viewModel.removeFav(FavStores(Datasource().getCurrentUserId(), store.id) )
                viewModel.myResponse_removeFav.observe(self, { response ->

                })

            } else {
                fav.setImageResource(R.mipmap.hearton)
                fav.tag = R.mipmap.hearton
                Datasource().addFavorite(store)
                Toast.makeText(
                    context,
                    "Store added to favorites",
                    Toast.LENGTH_SHORT
                ).show()

                lateinit var viewModel: MainViewModel
                val self = Datasource().getSELF()
                val repository = Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = self?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
                viewModel.saveFav(FavStores(Datasource().getCurrentUserId(), store.id) )
                viewModel.myResponse_FavStores.observe(self, { response ->

                })
            }

            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            val json: String = gson.toJson(Datasource().getFavoriteID())

            editor.putString("favorites", json)
            editor.commit()
        }
        info_button.setOnClickListener {
            var dialog = Dialog("Follow a Store", "When you are following a store you will get notified when it is the best time for you to go there", "Ok", DialogInterface.OnClickListener { dialog, which ->

            })
            dialog.isCancelable = false
            dialog.show(parentFragmentManager, "Follow Info")
        }

        // Create the Handler object (on the main thread by default)
        val handler = Handler()
        // Define the code block to be executed
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                //updateDB()
                current.text = Datasource().getStoreCurrentCount(store.name)
                waiting.text = Datasource().getStoreCurrentWaiting(store.name)
                //Log.d("Handlers", "Called on main thread")
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000)
            }
        }
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode)

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