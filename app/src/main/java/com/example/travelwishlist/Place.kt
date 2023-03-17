package com.example.travelwishlist

import java.text.SimpleDateFormat
import java.util.*


data class Place (val name: String, val field: String, val dateAdded: Date = Date()) {

    fun formattedDate(): String {
        return  SimpleDateFormat( "EEE, d MMMM yyyy", Locale.getDefault()).format(dateAdded)
    }
}