package com.example.travelwishlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwishlist.R.id.reason_edit_text
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class MainActivity() : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {
    private lateinit var newPlaceEditText: EditText
    private lateinit var addNewPlaceButton: Button
    private lateinit var placeListRecyclerView: RecyclerView
    private lateinit var etReason: EditText

    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter

    private val placesListModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        newPlaceEditText = findViewById(R.id.new_place_name)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        etReason = findViewById(reason_edit_text)

        val places = placesListModel.getPlaces()

        // Configure the RecyclerView
        placesRecyclerAdapter = PlaceRecyclerAdapter(places, this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        ItemTouchHelper(OnListItemSwipeListener(this))
            .attachToRecyclerView(placeListRecyclerView)

        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }
    }

    private fun addNewPlace() {
        val name = newPlaceEditText.text.toString().trim()
        val reason = etReason.text.toString().trim() // trim method removes blank spaces
        if (name.isEmpty() && reason.isEmpty()) {
            Toast.makeText(this, "Enter a place name and a reason", Toast.LENGTH_SHORT).show()
        } else {
            val place = Place(name, reason)
            val positionAdded = placesListModel.addNewPlace(place)

            if (positionAdded == -1) {
                Toast.makeText(this, "You already added that place", Toast.LENGTH_SHORT).show()
            } else {
                placesRecyclerAdapter.notifyItemInserted(positionAdded)
                clearForm()
                hideKeyboard()
            }
        }
    }

    // clearForm() method clears text from edit text newPlace and reason
    private fun clearForm() {
        newPlaceEditText.text.clear()
        etReason.text.clear()
    }

    fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        override fun onListItemClicked(place: Place) {
            showMapForPLace(place)
        }

        private fun showMapForPLace(place: Place) {
            Toast.makeText(
                this,
                getString(R.string.showing_map_message, place.name).Toast.LENGTH_LONG
            ).show()
            val placeLocationUri = Uri.parse("geo:0,0?q=${place}")
            val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
            startActivity(mapIntent)
        }

        override fun onListItemMoved(from: Int, to: Int) {
            placesListModel.movePlace(from, to)
            placesRecyclerAdapter.notifyItemMoved(from, to)
        }

        override fun onListItemDeleted(position: Int) {

            val place = placesListModel.deletePlace(position)
            placesRecyclerAdapter.notifyItemRemoved(position)

            Snackbar.make(findViewById(R.id.container),
                getString(R.string.place_deleted, place.name),
                Snackbar.LENGTH_LONG
                    .setActionTextColor(resources.getColor(R.color.red))
                    .setBackgroundTint(resources.getColor(R.color.black))
                    .setAction(getString(R.string.undo_delete)) {
                        placesListModel.addNewPlace(place, position)
                        placesRecyclerAdapter.notifyItemInserted(position)
                    }
                    .show())
        }

        companion object CREATOR : Parcelable.Creator<MainActivity> {
            override fun createFromParcel(parcel: Parcel): MainActivity {
                return MainActivity(parcel)
            }

            override fun newArray(size: Int): Array<MainActivity?> {
                return arrayOfNulls(size)
            }
        }

    }
}