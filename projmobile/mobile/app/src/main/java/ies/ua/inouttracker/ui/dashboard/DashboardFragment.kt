package ies.ua.inouttracker.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentDashboardBinding
import ies.ua.inouttracker.ui.adapter.StoreCardAdapter
import ies.ua.inouttracker.ui.model.StoreCard
import ies.ua.inouttracker.ui.store.StorePageFragment
import java.util.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

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
        val store: TextView = view.findViewById(R.id.mall_count_search)
        val actv_mall: ImageView = view.findViewById(R.id.actv2)

        createCards(view)

        mall.threshold = 2
        val adapter1: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, listOf("Mall1", "Mall2", "Mall3", "Mall4", "Mall5"))
        mall.setAdapter(adapter1)

        actv_mall.setOnClickListener {
            mall.showDropDown()
        }

    }

    private fun createCards(view: View?){
        val rv = view?.findViewById<RecyclerView>(R.id.stores_rv)
        var cards: MutableList<StoreCard> = mutableListOf<StoreCard>()

        for (i in 1..10){
            cards.add(StoreCard(R.drawable.ic_launcher_background, "Store$i", (0..10).random().toString(), (10..20).random().toString()))
        }

        Log.d("DEBUG:", cards.toString())

        if (rv != null) {
            rv.layoutManager = LinearLayoutManager(view?.context)
            var adapter = StoreCardAdapter(view.context, cards as ArrayList<StoreCard>)
            rv?.adapter = adapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}