package com.example.travelwishlist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PLACES_VIEW_FOR_MODEL"

class PlacesViewModel: ViewModel() {

    private val placeNames = mutableListOf<Place>(Place("Sydney, AU", " To see Kangaroo hopping"), Place("Patagonia, Chile","To see cows"), Place("Auckland, NZ", "dddd"))

    fun getPlaces(): List<Place> {
        return placeNames // smart cast
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {

        if (placeNames.any { placeNames -> placeNames.name.uppercase() == place.name.uppercase() }) {
            return -1 // avoid duplications by returning -1
        }
        return if (position == null) {
            placeNames.add(place) // adds at the end
            return placeNames.lastIndex
        } else {
            placeNames.add(position, place)
           return position
        }
    }

    fun addNewReason(place: Place, position: Int? = null): Int {
        if (placeNames.any { placeReason -> placeReason.reason.uppercase() == place.reason.uppercase() }) {
            return -1
        }
        return  if (position == null) {
            placeNames.add(place)
            return placeNames.lastIndex
        } else {
            placeNames.add(position, place)
            return position
        }

    }

    fun movePlace(from: Int, to: Int) {
        // Remove place and save value
        val place = placeNames.removeAt(from)
        // Insert into list at new position
        placeNames.add(to, place)
       Log.d(TAG, place.toString())
    }

    fun deletePlace(position: Int) : Place {
        return  placeNames.removeAt(position)
    }
}

