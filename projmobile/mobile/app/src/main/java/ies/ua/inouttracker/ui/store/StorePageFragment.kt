package ies.ua.inouttracker.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentStorePageBinding
import ies.ua.inouttracker.ui.dashboard.DashboardViewModel
import ies.ua.inouttracker.ui.model.Store
import ies.ua.inouttracker.util.Datasource
import java.net.DatagramSocket

class StorePageFragment(store: Store) : Fragment() {

    private lateinit var storePageViewModel: StorePageViewModel
    private var _binding: FragmentStorePageBinding? = null
    private var store = store

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
        }

    }
}