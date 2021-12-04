package ies.ua.inouttracker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentDashboardBinding

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
        val store: TextView = view.findViewById(R.id.store_count_search)
        val actv_mall: ImageView = view.findViewById(R.id.actv2)


        mall.threshold = 2
        val adapter1: ArrayAdapter<String> = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, listOf("Mall1", "Mall2", "Mall3", "Mall4", "Mall5"))
        mall.setAdapter(adapter1)

        actv_mall.setOnClickListener {
            mall.showDropDown()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}