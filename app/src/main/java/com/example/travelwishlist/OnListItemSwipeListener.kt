package com.example.travelwishlist

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface  OnDataChangedListener {
    fun onListItemMoved(from:Int, to:Int)
    fun onListItemDeleted(position:Int)

}

class OnListItemSwipeListener(private val onDataChangedListener: OnDataChangedListener):
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT) {

    private  var deleteBackground: ColorDrawable = ColorDrawable(Color.GRAY)

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

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView

        val deleteIcon = ContextCompat.getDrawable(itemView.context, android.R.drawable.ic_delete)

        if (isCurrentlyActive && dX > 0)  {  // right swipe in progress. If dX < 0 then this is a left swipe.

            // Set background to same size as view being swiped and draw it
            deleteBackground.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
            deleteBackground.draw(c)

            // Get the delete icon, which may be null. If not null, set size and draw on background
            deleteIcon?.let { icon ->  // icon is the deleteIcon, as a non-nullable Drawable, but only if it is not null.
                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2  // distance from edge of background
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
                val iconTop = itemView.top + iconMargin
                val iconBottom = iconTop + icon.intrinsicHeight

                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                icon.draw(c)
            }
        }else {
            // View not being swiped right, or it is being moved up/down, or it has been released and
            // is animating back to the original position.
            // Background and delete icon should not be displayed.
            // Set sizes of background and icon to 0 and draw.
            deleteIcon?.setBounds(0, 0, 0, 0)
            deleteIcon?.draw(c)
            deleteBackground.setBounds(0, 0, 0, 0)
            deleteBackground.draw(c)
        }
    }
}

