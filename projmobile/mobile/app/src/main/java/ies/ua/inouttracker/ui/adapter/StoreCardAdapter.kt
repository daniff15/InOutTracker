package ies.ua.inouttracker.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.R
import ies.ua.inouttracker.ui.dashboard.DashboardFragment
import ies.ua.inouttracker.ui.model.StoreCard
import java.util.ArrayList

class StoreCardAdapter (private val context: Context, private val StoreCards: ArrayList<StoreCard>) : RecyclerView.Adapter<StoreCardAdapter.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        // to inflate the layout for each item of recycler view.
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.store_card_layout, parent, false)
        return Viewholder(view)

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        // to set data to textview and imageview of each card layout
        val store: StoreCard = StoreCards[position]
        holder.current.text = store.count
        holder.max.text = store.maxCap
        holder.name.text = store.name
        holder.click.setOnClickListener {
            DashboardFragment().openStorePage(holder.itemView)
        }

        //holder.logo.setImageResource(store.logo)
    }

    override fun getItemCount(): Int {
        // this method is used for showing number
        // of card items in recycler view.
        return StoreCards.size
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView
        val current: TextView
        val max: TextView
        val name: TextView
        val click: Button

        init {
            logo = itemView.findViewById((R.id.store_logo))
            name = itemView.findViewById(R.id.store_name)
            current = itemView.findViewById(R.id.current_capacity)
            max = itemView.findViewById(R.id.max_capacity)
            click = itemView.findViewById(R.id.go_to_store_page)
        }
    }

}