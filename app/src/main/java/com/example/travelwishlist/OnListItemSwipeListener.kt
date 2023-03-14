package com.example.travelwishlist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface  OnDataChangedListener {
    fun onListItemMoved(from:Int, to:Int)
    fun onListItemMoved(position:Int)

}

class OnListItemSwipeListener(private val onDataChangedListener: OnDataChangedListener):
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // Move up and down to rearrange
        // For handling up/down movements

       val fromPosition = viewHolder.adapterPosition
       val toPosition = target.adapterPosition
       onDataChangedListener.onListItemMoved(fromPosition,toPosition)
       return true // return true if item is moved, false otherwise
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.RIGHT) {
            onDataChangedListener.onListItemDeleted(viewHolder.adapterPosition)
        }
    }
}