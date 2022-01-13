package ies.ua.inouttracker.ui.store

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
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
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentStorePageBinding
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.dashboard.DashboardViewModel
import ies.ua.inouttracker.ui.model.Dialog
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.util.Datasource
import java.net.DatagramSocket

class StorePageFragment(store: Store) : Fragment() {

    private lateinit var storePageViewModel: StorePageViewModel
    private var _binding: FragmentStorePageBinding? = null
    private var store = store
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
        val max = view.findViewById<TextView>(R.id.mall_count_current_count2)

        Datasource().getStoreLogo(store.name)?.let { store_logo.setImageResource(it) }
        store_name.text = store.name
        current.text = store.people_count.toString()
        max.text = store.max_capacity.toString()

        val fav = view?.findViewById<ImageButton>(R.id.favorite)

        if (store in Datasource().getFavorite()) {
            fav.setImageResource(R.mipmap.hearton)
            fav.tag = R.mipmap.hearton
        }

        follow.setOnClickListener {
            var dialog: Dialog = Dialog()
            //fragmentManager?.let { it1 -> dialog.show(it1, "dialog") }

            Datasource().addFollowing(store, 1)

            Toast.makeText(
                context,
                "You are now following this store",
                Toast.LENGTH_SHORT
            ).show()
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
            } else {
                fav.setImageResource(R.mipmap.hearton)
                fav.tag = R.mipmap.hearton
                Datasource().addFavorite(store)
                Toast.makeText(
                    context,
                    "Store added from favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            val json: String = gson.toJson(Datasource().getFavorite())

            editor.putString("favorites", json)
            editor.commit()
        }
        // Create the Handler object (on the main thread by default)
        val handler = Handler()
        // Define the code block to be executed
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                updateDB()
                current.text = Datasource().getStoreCurrentCount(store.name)
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