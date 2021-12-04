package ies.ua.inouttracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

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
        val mall: AutoCompleteTextView = view.findViewById(R.id.choose_mall)
        val store: AutoCompleteTextView = view.findViewById(R.id.choose_store)
        val actv_mall: ImageView = view.findViewById(R.id.actv1)
        val actv_store: ImageView = view.findViewById(R.id.actv)


        mall.threshold = 2
        store.threshold = 2

        val adapter1: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, listOf("Mall1", "Mall2", "Mall3", "Mall4", "Mall5"))
        mall.setAdapter(adapter1)
        val adapter2: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, listOf("Store1", "Store2", "Store3", "Store4", "Store5"))
        store.setAdapter(adapter2)

        actv_mall.setOnClickListener {
            mall.showDropDown()
        }

        actv_store.setOnClickListener {
            store.showDropDown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}