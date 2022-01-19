package ies.ua.inouttracker.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentHomeBinding
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.adapter.StoreCardAdapter
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.StoreCard
import ies.ua.inouttracker.util.Datasource
import java.util.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: MainViewModel
    private var selfcont = this



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val store_capacity: TextView = view.findViewById(R.id.store_count)
        val mall_capacity: TextView = view.findViewById(R.id.mall_count)

        val mall: AutoCompleteTextView = view.findViewById(R.id.choose_mall)
        val store: AutoCompleteTextView = view.findViewById(R.id.choose_store)
        val actv_mall: ImageView = view.findViewById(R.id.actv1)
        val actv_store: ImageView = view.findViewById(R.id.actv)

        createCards(view)

        mall.threshold = 2
        store.threshold = 2

        val stores = Datasource().getAllStores()
        val shoppings = Datasource().getAllShoppings()

        var selected_store: String = ""
        var selected_mall: String = ""


        val adapter1: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, shoppings)
        mall.setAdapter(adapter1)
        val adapter2: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, stores)
        store.setAdapter(adapter2)

        mall.setOnItemClickListener { parent, view, position, id ->
            selected_mall = shoppings[position]
            mall_capacity.text = Datasource().getShoppingCurrentCount(selected_mall)
        }

        store.setOnItemClickListener { parent, view, position, id ->
            selected_store = stores[position]
            store_capacity.text = Datasource().getStoreCurrentCount(selected_store)
        }

        actv_mall.setOnClickListener {
            mall.showDropDown()
        }

        actv_store.setOnClickListener {
            store.showDropDown()
        }

        // Create the Handler object (on the main thread by default)
        val handler = Handler()
        // Define the code block to be executed
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                //updateDB(selfcont)
                if (selected_store != "") store_capacity.text = Datasource().getStoreCurrentCount(selected_store)
                if (selected_mall != "") mall_capacity.text = Datasource().getShoppingCurrentCount(selected_mall)
                //createCards(view)
                //Log.d("Handlers", "Called on main thread")
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 5000)
            }
        }
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode)
    }

    private fun createCards(view: View?){
        val rv = view?.findViewById<RecyclerView>(R.id.home_rv)
        var cards: MutableList<StoreCard> = mutableListOf<StoreCard>()
        
        for (store in Datasource().getFavorite()){
            var new_store = Datasource().getStoreById(store.id)
            if (new_store != null) {
                Datasource().getStoreLogo(new_store.name)?.let {
                    StoreCard(new_store.id,
                        it, Datasource().getShoppingById(new_store.shop_id), new_store.name, new_store.people_count.toString(), new_store.max_capacity.toString(), 1)
                }?.let { cards.add(it) }
            }
        }

        if (rv != null) {
            rv.layoutManager = LinearLayoutManager(view?.context)
            var adapter = StoreCardAdapter(view.context, cards as ArrayList<StoreCard>)
            rv?.adapter = adapter
        }

    }

    fun updateDB(selfcont: HomeFragment) {
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
        for (following in Datasource().getFollowing()){
            if (following.key.people_count == following.key.max_capacity * following.value){
                Datasource().removeFollowing(following.key)
                var follow_not = selfcont.context?.let { it1 ->
                    NotificationCompat.Builder(it1, "Notify")
                        .setSmallIcon(R.drawable.notification_bell)
                        .setContentTitle("Test Notification")
                        .setContentText("Some big test to describe what's happening")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel("Notify", "Notify", NotificationManager.IMPORTANCE_DEFAULT).apply {
                        description = "description"
                    }
                    // Register the channel with the system
                    val notificationManager: NotificationManager =
                        Datasource().getSELF()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                with(selfcont.context?.let { it1 -> NotificationManagerCompat.from(it1) }) {
                    // notificationId is a unique int for each notification that you must define
                    if (follow_not != null) {
                        this?.notify(0, follow_not.build())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openStorePage(view: View, store: Store) {
        Datasource().setCurrentStore(store)
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_storePageFragment)
    }
}