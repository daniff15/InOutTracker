package ies.ua.inouttracker.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import ies.ua.inouttracker.R
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
        /** TEMPLATE FOR THE STORE CARD
        holder.from.text = route.from
        holder.to.text = route.to
        holder.time.text = route.time
        holder.id.tag = route.unique_id

        route.img?.let { holder.company.setImageResource(it) }
        holder.click.setOnClickListener {
            if (op == 2){
                HomeFragment().openFavRoute(holder.from.text.toString(), holder.to.text.toString(), holder.itemView)
            }
            else
                route.info?.let { it1 -> SearchFragment().openRoutePage(holder.id.text.toString(), holder.from.text.toString(), holder.to.text.toString(), holder.time.text.toString(), holder.itemView, op, day, info = it1, unique_id = route.unique_id) }
        }
        **/
    }

    override fun getItemCount(): Int {
        // this method is used for showing number
        // of card items in recycler view.
        return StoreCards.size
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** TEMPLATE FOR THE STORE CARD

        val id: TextView
        val from: TextView
        val to: TextView
        val time: TextView
        val company: ImageView
        val click: Button
        val delete: ImageButton

        init {
            id = itemView.findViewById(R.id.route_id)
            from = itemView.findViewById(R.id.route_origin)
            to = itemView.findViewById(R.id.route_destination)
            time = itemView.findViewById(R.id.route_time)
            company = itemView.findViewById(R.id.route_company)
            click = itemView.findViewById(R.id.go_to_route_page)
            delete = itemView.findViewById(R.id.remove_fav_home)

        }
        **/
    }

}