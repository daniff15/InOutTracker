package ies.ua.inouttracker.ui.notifications

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.Group
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.gson.Gson
import ies.ua.inouttracker.R
import ies.ua.inouttracker.databinding.FragmentNotificationsBinding
import ies.ua.inouttracker.util.Datasource
import ies.ua.inouttracker.util.capacity_check

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
        val notify: Switch = view.findViewById(R.id.notification_switch)
        val notify_group: Group = view.findViewById(R.id.notify_group)

        val percentage: EditText = view.findViewById(R.id.notify_percentage)
        val capacity_custom: CheckBox = view.findViewById(R.id.percentage_checkbox)
        val empty: CheckBox = view.findViewById(R.id.isEmpty_checkbox)
        val full: CheckBox = view.findViewById(R.id.Full_checkbox)

        percentage.setText(Datasource().getPercentage().toString())

        notify.isChecked = Datasource().getNotified()
        capacity_custom.isChecked = Datasource().getCapacity_check()
        empty.isChecked = Datasource().getIsEmpty_check()
        full.isChecked = Datasource().getIsFull_check()

        if (notify.isChecked) notify_group.visibility = View.VISIBLE
        else notify_group.visibility = View.INVISIBLE

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


        notify.setOnCheckedChangeListener { buttonView, isChecked ->
            Datasource().setNotified(isChecked)
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            val json: String = gson.toJson(isChecked.toString())

            editor.putString("notify", json)
            editor.commit()
            if (notify.isChecked) notify_group.visibility = View.VISIBLE
            else notify_group.visibility = View.INVISIBLE
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

        percentage.doOnTextChanged { text, start, before, count ->
            val int: Int? = text.toString().toIntOrNull()
            if (int != null){
                Datasource().setPercentage(int)
                val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val editor = pref.edit()
                val gson = Gson()

                val json: String = gson.toJson(int)

                editor.putString("percentage", json)
                editor.commit()
            }
        }
        capacity_custom.setOnClickListener {
            Datasource().setCapacity_check(capacity_custom.isChecked)
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            var json = if(capacity_custom.isChecked) gson.toJson("true") else gson.toJson("false")

            editor.putString("custom", json)
            editor.commit()
        }
        empty.setOnClickListener {
            Datasource().setCapacity_check(capacity_custom.isChecked)
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            var json = if(empty.isChecked) gson.toJson("true") else gson.toJson("false")

            editor.putString("empty", json)
            editor.commit()
        }
        full.setOnClickListener {
            Datasource().setCapacity_check(capacity_custom.isChecked)
            val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            val gson = Gson()

            var json = if(full.isChecked) gson.toJson("true") else gson.toJson("false")

            editor.putString("full", json)
            editor.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}