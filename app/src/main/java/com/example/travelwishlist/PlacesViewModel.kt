package com.example.travelwishlist

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

    private val places = mutableListOf(Place("Auckland"), Place("Patagonia"))

    fun getPlaces(): List<Place> {
        return places // smart cast
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {


        if (places.any { it.name.uppercase() == place.name.uppercase() }) {
            return -1 // avoid duplications by returning -1
        }

        return if (position != null) {
            places.add(position, place) // adds at the end
            position
            places.lastIndex
        } else {
            places.add(place)
            places.lastIndex
        }
    }

    fun movePLace(from: Int, to: Int) {
        // Remove place and save value
        val place = places.removeAt(from)
        // Insert into list at new position
        places.add(to, place)
    }

    fun deletePlace(position: Int) : Place {
        return  places.removeAt(position)
    }
}

