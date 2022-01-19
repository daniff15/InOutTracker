package ies.ua.inouttracker.ui.model

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import ies.ua.inouttracker.R

class Dialog (var title: String, var message: String, var positive: String, var onClick: DialogInterface.OnClickListener, var negative: String? = null, var dismiss: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->  }) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = android.app.AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(positive, onClick)
            .setNegativeButton(negative, dismiss)
            .setCancelable(false)
        return builder.create()
    }
}