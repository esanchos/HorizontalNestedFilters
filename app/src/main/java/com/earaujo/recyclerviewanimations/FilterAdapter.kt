package com.earaujo.recyclerviewanimations

import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.transition.TransitionSet.ORDERING_TOGETHER
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
            addEveryOtherItem(itemSelected)
            itemSelected = -1
        }

    }

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.filterTextView.text = items[layoutPosition]
            updateLayout()
            itemView.setOnClickListener {
                if (itemSelected < 0) removeItems() else addItems()
                selectData(layoutPosition)
                if (itemSelected < 0) callback(null) else callback(itemView)
                updateLayout()
            }
        }

        private fun removeItems() {
            val transition = TransitionSet().apply {
                duration = 200
                ordering = ORDERING_TOGETHER
                startDelay = 200
                excludeChildren(itemView, true)
                excludeTarget(itemView, true)
                addTransition(ChangeBounds())
                addListener(object : Transition.TransitionListener {
                    override fun onTransitionEnd(p0: Transition?) {
                        /*(itemView.parent as ViewGroup).background = TransitionDrawable(
                            arrayOf(
                                itemView.context.getBitmap(R.drawable.shape_recycler_background),
                                itemView.context.getBitmap(R.drawable.ic_divider)
                            )
                        ).apply {
                            startTransition(100)
                        }*/
                    }

                    override fun onTransitionResume(p0: Transition?) {
                    }

                    override fun onTransitionPause(p0: Transition?) {
                    }

                    override fun onTransitionCancel(p0: Transition?) {
                    }

                    override fun onTransitionStart(p0: Transition?) {
                    }
                })
            }
            TransitionManager.beginDelayedTransition(itemView.parent as ViewGroup, transition)
        }

        private fun addItems() {
            /*(itemView.parent as ViewGroup).background = TransitionDrawable(
                arrayOf(
                    itemView.context.getBitmap(R.drawable.ic_divider),
                    itemView.context.getBitmap(R.drawable.shape_recycler_background)
                )
            ).apply {
                startTransition(0)
            }*/
            val transition = TransitionSet().apply {
                duration = 200
                ordering = ORDERING_TOGETHER
                excludeChildren(itemView, true)
                excludeTarget(itemView, true)
                addTransition(ChangeBounds())
            }
            TransitionManager.beginDelayedTransition(itemView.parent as ViewGroup, transition)
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