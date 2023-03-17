package com.example.travelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface  OnListItemClickedListener {
    fun onListItemClicked(place: Place)
}

class PlaceRecyclerAdapter(private val places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener
):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // manages one view - one list item - sets the actual data in the view
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(place:Place, onListItemClickedListener: OnListItemClickedListener){
            val placeNameTextView: TextView = view.findViewById(R.id.place_name)
            placeNameTextView.text = place.name

            val etReason: TextView = view.findViewById(R.id.reason_edit_text)
            etReason.text = place.field

            val dateCreatedOnText = view.findViewById<TextView>(R.id.date_place_added)
            dateCreatedOnText.text = view.context.getString(R.string.created_on, place.formattedDate())

            val mapIcon: ImageView = view.findViewById(R.id.map_icon)
            mapIcon.setOnClickListener {
                onListItemClickedListener.onListItemClicked(place)
            }
        }
    }

    // called by the recycler view to create as many view holders that are needed to
    // display the list on screen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from( parent.context)
            .inflate(R.layout.place_list_item, parent, false)
        return ViewHolder(view)
    }

    // called by the recycler view to set the data for one list item, by position
    override fun onBindViewHolder(holder: ViewHolder, postition: Int) {
       val place = places[position]
        holder.bind(place)
    }

    // how many views to display?
    override fun getItemCount(): Int {
        return places.size
    }
}