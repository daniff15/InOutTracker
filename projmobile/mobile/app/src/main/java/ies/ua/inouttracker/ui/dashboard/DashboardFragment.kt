package ies.ua.inouttracker.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.MainViewModel
import ies.ua.inouttracker.MainViewModelFactory
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentDashboardBinding
import ies.ua.inouttracker.repository.Repository
import ies.ua.inouttracker.ui.adapter.StoreCardAdapter
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.ui.model.StoreCard
import ies.ua.inouttracker.ui.store.StorePageFragment
import ies.ua.inouttracker.util.Datasource
import java.util.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var viewModel: MainViewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mall: AutoCompleteTextView = view.findViewById(R.id.choose_mall_search)
        val count: TextView = view.findViewById(R.id.mall_count_search)
        val actv_mall: ImageView = view.findViewById(R.id.actv_dashboard)
        val vieww = view

        val shoppings = Datasource().getAllShoppings()
        val stores = Datasource().getAllStores()

        var selected_mall: String = ""


        if (selected_mall != "")
        {
            createCards(vieww, selected_mall)
            count.text = Datasource().getShoppingCurrentCount(selected_mall)
        }

        mall.threshold = 2
        val adapter1: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, shoppings)
        mall.setAdapter(adapter1)

        actv_mall.setOnClickListener {
            mall.showDropDown()
        }

        mall.setOnItemClickListener { parent, view, position, id ->
            selected_mall = shoppings[position]
            count.text = Datasource().getShoppingCurrentCount(selected_mall)

            createCards(vieww, selected_mall)

        }

        // Create the Handler object (on the main thread by default)
        val handler = Handler()
        // Define the code block to be executed
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                //updateDB()
                if (selected_mall != ""){
                    count.text = Datasource().getShoppingCurrentCount(selected_mall)
                    //Datasource().getShoppingStores(Datasource().getShoppingId(selected_mall))?.let { createCards(view, it) }
                }
                //Log.d("Handlers", "Called on main thread")
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 5000)
            }
        }
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode)
    }

    private fun createCards(view: View?, mall: String){
        val rv = view?.findViewById<RecyclerView>(R.id.stores_rv)
        var cards: MutableList<StoreCard> = mutableListOf<StoreCard>()
        val stores = Datasource().getShoppingStores(Datasource().getShoppingId(mall))
        Log.d("debug",
            stores.toString()
        )
        if (stores != null) {
            for (store in stores){
                cards.add(StoreCard(Datasource().getStoreID(store), R.drawable.ic_launcher_background, "", store.name, store.people_count.toString(), store.max_capacity.toString()))
            }
        }

        if (rv != null) {
            Log.d("debug",
                "depois:" + cards.toString()
            )
            rv.layoutManager = LinearLayoutManager(view?.context)
            var adapter = StoreCardAdapter(view.context, cards as ArrayList<StoreCard>)
            rv?.adapter = adapter
        }
        else{
            Log.d("debug",
                "rv null"
            )
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

    fun openStorePage(view: View, store: Store){
        Datasource().setCurrentStore(store)
        Navigation.findNavController(view).navigate(R.id.action_navigation_dashboard_to_storePageFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}