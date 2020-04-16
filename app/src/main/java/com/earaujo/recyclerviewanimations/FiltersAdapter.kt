package com.earaujo.recyclerviewanimations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_filters.view.*

class FiltersAdapter(
    private val items: List<List<String>>,
    private val callback: (View?, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return getItemViewHolder(parent, inflater)
    }

    private fun getItemViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_list_filters, parent, false)
        return FiltersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FiltersViewHolder).bind()
    }

    override fun getItemCount() = items.size

    inner class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.filterRecyclerView.adapter =
                FilterAdapter(items[layoutPosition]) {
                    callback(it, layoutPosition)
                }

            val duration = itemView.resources.getInteger(R.integer.anim_duration_filter).toLong()
            itemView.filterRecyclerView.itemAnimator?.apply {
                moveDuration = duration
                addDuration = duration
                removeDuration = duration
                changeDuration = duration
            }
        }

    }

}
