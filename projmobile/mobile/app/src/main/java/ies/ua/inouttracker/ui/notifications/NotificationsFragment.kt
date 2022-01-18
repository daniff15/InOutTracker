package ies.ua.inouttracker.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.gson.Gson
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentNotificationsBinding
import ies.ua.inouttracker.util.Datasource

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val login: Button = view.findViewById(R.id.login)
        val sign_up: Button = view.findViewById(R.id.sign_up)
        val logout: Button = view.findViewById(R.id.logout)

        val current_user: TextView = view.findViewById(R.id.loggedin_user)
        current_user.text = Datasource().getCurrentUser()

        if (Datasource().isLoggedIn()){
            login.visibility = View.GONE
            sign_up.visibility = View.GONE
            logout.visibility = View.VISIBLE
        }
        else {
            login.visibility = View.VISIBLE
            sign_up.visibility = View.VISIBLE
            logout.visibility = View.GONE
        }

        login.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_notifications_to_loginFragment)
        }
        logout.setOnClickListener {
            Datasource().setLoggedIn(false, "")
            Datasource().setCurrentUserId(-1)
            current_user.text = ""
            login.visibility = View.VISIBLE
            sign_up.visibility = View.VISIBLE
            logout.visibility = View.GONE
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            val json: String = gson.toJson("")
            val id: String? = gson.toJson(Datasource().getCurrentUserId())

            editor.putString("loggedin", json)
            editor.putString("user_id", id)
            editor.commit()
        }
        sign_up.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_notifications_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}