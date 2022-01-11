package ies.ua.inouttracker.ui.model

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import ies.ua.inouttracker.R

class Dialog: AppCompatDialogFragment() {
    private lateinit var percentage: EditText
    @Override
    fun onCreateDialog(savedInstanceState: Bundle): AlertDialog? {
        var builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }

        var inflater : LayoutInflater? = activity?.layoutInflater
        var view: View? = inflater?.inflate(R.layout.layout_dialog, null)
        
        builder?.setView(view)
            ?.setTitle("Get Notified")
            ?.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })
            ?.setPositiveButton("Follow", DialogInterface.OnClickListener { dialog, which ->  })

        percentage = view?.findViewById(R.id.percentage_to_notify)!!

        return builder?.create()
    }
}