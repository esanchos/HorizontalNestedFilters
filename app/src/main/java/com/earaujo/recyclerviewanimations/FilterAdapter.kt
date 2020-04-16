package com.earaujo.recyclerviewanimations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_filter.view.*

class FilterAdapter(
    private val items: List<String>,
    private val callback: (View?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return getItemViewHolder(parent, inflater)
    }

    private fun getItemViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_list_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FilterViewHolder).bind()
    }

    override fun getItemCount() = if (itemSelected < 0) items.size else 1

    private fun removeEveryOtherItem(position: Int) {
        var itemToRemove = 0
        for (index in items.indices) {
            if (position == index) {
                itemToRemove = 1
            } else {
                notifyItemRemoved(itemToRemove)
            }
        }
    }

    private fun addEveryOtherItem(position: Int) {
        for (index in items.indices) {
            if (position != index) {
                notifyItemInserted(index)
            }
        }
    }

    private fun selectData(position: Int) {
        if (itemSelected < 0) {
            itemSelected = position
            removeEveryOtherItem(position)
        } else {
            val oldSelection = itemSelected
            itemSelected = -1
            addEveryOtherItem(oldSelection)
        }
    }

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.filterTextView.text = items[layoutPosition]
            updateLayout()
            itemView.setOnClickListener {
                if (itemSelected < 0) callback(null) else callback(itemView)
                selectData(layoutPosition)
                updateLayout()
            }
        }

        private fun updateLayout() {
            if (itemSelected < 0) {
                itemView.filterTextView.background = null
            } else {
                itemView.filterTextView.background = AppCompatResources.getDrawable(
                    itemView.context,
                    R.drawable.shape_selected_filter
                )
            }
        }

        /*private fun Context.getBitmap(bitmapId: Int) =
            AppCompatResources.getDrawable(this, bitmapId)*/
    }

}