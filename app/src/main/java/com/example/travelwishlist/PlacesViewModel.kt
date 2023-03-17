package com.example.travelwishlist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PLACES_VIEW_FOR_MODEL"

class PlacesViewModel: ViewModel() {

    private val places = mutableListOf(Place("Sydney, AU", " To see Kangaroo hopping") )

    fun getPlaces(): List<Place> {
        return places // smart cast
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {

        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() }) {
            return -1 // avoid duplications by returning -1
        }
        return if (position == null) {
            places.add(place) // adds at the end
            return places.lastIndex
        } else {
            places.add(position, place)
           return position
        }
    }

    fun addNewReason(place: Place, position: Int? = null): Int {
        if (places.any { placeReason -> placeReason.reason.uppercase() == place.reason.uppercase() }) {
            return -1
        }
        return  if (position == null) {
            places.add(place)
            return places.lastIndex
        } else {
            places.add(position, place)
            return position
        }

    }

    fun movePLace(from: Int, to: Int) {
        // Remove place and save value
        val place = places.removeAt(from)
        // Insert into list at new position
        places.add(to, place)
       Log.d(TAG, place.toString())
    }

    fun deletePlace(position: Int) : Place {
        return  places.removeAt(position)
    }
}

