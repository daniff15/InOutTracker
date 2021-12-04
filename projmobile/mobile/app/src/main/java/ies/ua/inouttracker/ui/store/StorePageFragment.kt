package ies.ua.inouttracker.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentStorePageBinding
import ies.ua.inouttracker.ui.dashboard.DashboardViewModel

class StorePageFragment : Fragment() {

    private lateinit var storePageViewModel: StorePageViewModel
    private var _binding: FragmentStorePageBinding? = null

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

    }

    }