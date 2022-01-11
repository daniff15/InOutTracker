package ies.ua.inouttracker.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentHomeBinding
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.adapter.StoreCardAdapter
import ies.ua.inouttracker.ui.model.StoreCard
import ies.ua.inouttracker.util.Datasource
import org.w3c.dom.Text
import java.util.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: MainViewModel


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
                updateDB()
                if (selected_store != "") store_capacity.text = Datasource().getStoreCurrentCount(selected_store)
                if (selected_mall != "") mall_capacity.text = Datasource().getShoppingCurrentCount(selected_mall)
                Log.d("Handlers", "Called on main thread")
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000)
            }
        }
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode)

    }

    private fun createCards(view: View?){
        val rv = view?.findViewById<RecyclerView>(R.id.home_rv)
        var cards: MutableList<StoreCard> = mutableListOf<StoreCard>()

        for (store in Datasource().getFavorite()){
            cards.add(StoreCard(Datasource().getStoreID(store), R.drawable.ic_launcher_background, Datasource().getShoppingById(store.shop_id), store.name, store.people_count.toString(), store.max_capacity.toString()))
        }

        if (rv != null) {
            rv.layoutManager = LinearLayoutManager(view?.context)
            var adapter = StoreCardAdapter(view.context, cards as ArrayList<StoreCard>)
            rv?.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}